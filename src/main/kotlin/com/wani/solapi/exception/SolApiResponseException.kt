package com.wani.solapi.exception

import com.wani.solapi.response.ErrorResponse
import retrofit2.HttpException

class SolApiResponseException(
    errorResponse: ErrorResponse,
    exception: HttpException
) : Exception(errorResponse.errorMessage, exception)