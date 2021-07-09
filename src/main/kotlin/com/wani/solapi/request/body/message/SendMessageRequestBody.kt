package com.wani.solapi.request.body.message

data class SendMessageRequestBody(
    val message: RequestMessage
)

data class RequestMessage(
    val to: String,
    val from: String,
    val text: String
)
