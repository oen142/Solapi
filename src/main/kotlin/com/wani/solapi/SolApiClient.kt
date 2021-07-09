package com.wani.solapi

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.wani.solapi.exception.SolApiResponseException
import com.wani.solapi.request.body.message.SendMessageRequestBody
import com.wani.solapi.response.ErrorResponse
import com.wani.solapi.response.message.SendMessageResponse
import okhttp3.OkHttpClient
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.URL
import java.util.concurrent.TimeUnit

class SolApiClient(
    apiKey: String,
    apiSecret: String
) {
    private val solApi: SolApi
    private val auth: String

    init {
        val client = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(URL(API_URL))
            .addConverterFactory(buildGsonConverter())
            .client(client)
            .build()
        solApi = retrofit.create(SolApi::class.java)
        auth = ApiSignatureFactory.getAuthorization(
            apiKey = apiKey,
            apiSecret = apiSecret,
            ApiSignatureFactory.saltGenerator(),
            ApiSignatureFactory.dateGenerator()
        )
    }

    private fun buildGsonConverter(): GsonConverterFactory {
        val gsonBuilder = GsonBuilder()

        val gson = gsonBuilder.create()

        return GsonConverterFactory.create(gson)
    }

    private fun getExceptionMessage(response: Response<*>): ErrorResponse =
        try {
            val element = JsonParser().parse(response.errorBody()?.string())
            ErrorResponse(
                errorCode = element.asJsonObject.get("errorCode").asString,
                errorMessage = element.asJsonObject.get("errorMessage").asString
            )
        } catch (e: JsonSyntaxException) {
            ErrorResponse(
                errorCode = "UNKNOWN JsonSyntaxException",
                errorMessage = e.message.toString()
            )
        } catch (e: IOException) {

            ErrorResponse(
                errorCode = "UNKNOWN IOException",
                errorMessage = e.message.toString()
            )
        }

    @Throws(SolApiResponseException::class, IOException::class)
    fun sendMessage(request: SendMessageRequestBody): SendMessageResponse? {
        val call = solApi.sendMessage(
            auth = auth,
            request = request
        )

        val response = call.execute()

        if (!response.isSuccessful) {

            throw SolApiResponseException(getExceptionMessage(response), HttpException(response))
        }
        return response.body()
    }

    companion object {
        private const val API_URL = "https://api.solapi.com/"
    }


}