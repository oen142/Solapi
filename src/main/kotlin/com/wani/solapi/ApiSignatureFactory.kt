package com.wani.solapi

import org.apache.commons.codec.binary.Hex
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object ApiSignatureFactory {

    fun getAuthorization(apiKey: String, apiSecret: String, salt: String, date: String): String {
        val sha256HMAC = Mac.getInstance("HmacSHA256")
        val secretKeySpec = SecretKeySpec(apiSecret.toByteArray(StandardCharsets.UTF_8), "HmacSHA256")
        sha256HMAC.init(secretKeySpec)

        val signature =
            String(Hex.encodeHex(sha256HMAC.doFinal((date + salt).toByteArray(StandardCharsets.UTF_8))))

        return "HMAC-SHA256 ApiKey=$apiKey,Date=$date,salt=$salt,signature=$signature"
    }

     fun saltGenerator(): String =
        UUID.randomUUID().toString().replace("-", "")

     fun dateGenerator(): String =
        LocalDateTime.parse(LocalDateTime.now(ZoneId.of("Asia/Seoul")).toString()).toString()
}