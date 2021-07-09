package test.com.wani.solapi

import com.wani.solapi.ApiSignatureFactory
import com.wani.solapi.SolApi
import com.wani.solapi.SolApiClient
import com.wani.solapi.request.body.message.RequestMessage
import com.wani.solapi.request.body.message.SendMessageRequestBody
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.kotlin.MockitoKotlinException
import java.util.*

class ApiSignatureFactoryTest(

) {

    @BeforeEach
    fun setUp(){

    }


    @Test
    fun `시그니쳐 생성테스트`() {

        val replace = UUID.randomUUID().toString().replace("-", "")
        println("replace = ${replace}")

        Assertions.assertEquals(replace.length, 32)
    }

    @Test
    fun `sendTest`() {

        val response = SolApiClient("NCSZ4FUE8RYJPAAQ", "HDK14GUJ8HM1IHJFF6VFNXRQSDWQKXN4").sendMessage(
            SendMessageRequestBody(RequestMessage("01075045009", "07088568880", "테스트메시지입니다."))
        )

        println("response = ${response}")
        println("response = ${response?.groupId}")
        println("response = ${response?.to}")
        println("response = ${response?.from}")
        println("response = ${response?.country}")

    }
}