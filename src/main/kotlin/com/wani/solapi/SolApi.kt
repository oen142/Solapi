package com.wani.solapi

import com.wani.solapi.request.body.message.SendMessageRequestBody
import com.wani.solapi.response.message.SendMessageResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface SolApi {

    @POST("messages/v4/send")
    fun sendMessage(
        @Header("Content-Type")
        contentType: String = "application/json",
        @Header("Authorization")
        auth: String,
        @Body
        request: SendMessageRequestBody
    ): Call<SendMessageResponse>
}