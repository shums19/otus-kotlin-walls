package ru.otus.kotlin.walls.app.auth

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.*
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.jackson.jackson
import io.ktor.server.testing.*
import ru.otus.kotlin.walls.api.v1.models.AdCreateObject
import ru.otus.kotlin.walls.api.v1.models.AdCreateRequest
import ru.otus.kotlin.walls.api.v1.models.AdDebug
import ru.otus.kotlin.walls.api.v1.models.AdRequestDebugMode
import ru.otus.kotlin.walls.api.v1.models.AdRequestDebugStub
import ru.otus.kotlin.walls.api.v1.models.BuildingType
import ru.otus.kotlin.walls.api.v1.models.Status
import ru.otus.kotlin.walls.api.v1.models.Type
import ru.otus.kotlin.walls.app.AuthConfig
import ru.otus.kotlin.walls.app.helpers.testSettings
import ru.otus.kotlin.walls.app.prepare

class AuthTest : FreeSpec({
    fun ApplicationTestBuilder.prepareClient() = createClient {
        install(ContentNegotiation) {
            jackson {
                disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

                enable(SerializationFeature.INDENT_OUTPUT)
                writerWithDefaultPrettyPrinter()
            }
        }
    }

    "invalid audience" {
        testApplication {
            application {
                prepare(settings = testSettings())
            }
            val client = prepareClient()

            val response = client.post("/v1/ad/create") {
                val requestObj = AdCreateRequest(
                    requestId = "12345",
                    debug = AdDebug(
                        mode = AdRequestDebugMode.STUB,
                        stub = AdRequestDebugStub.SUCCESS,
                    ),
                    ad = AdCreateObject(
                        title = "title",
                        description = "desc",
                        active = true,
                        hasLift = true,
                        area = 52.toBigDecimal(),
                        price = 3250000.toBigDecimal(),
                        roomsNumber = 3,
                        floor = 4,
                        type = Type.APARTMENT,
                        status = Status.NEW,
                        buildingType = BuildingType.BRICK,
                    ),
                )
                addAuth(config = AuthConfig.TEST.copy(audience = "invalid"))
                contentType(ContentType.Application.Json)
                setBody(requestObj)
            }

            response.status shouldBe HttpStatusCode.Unauthorized
        }
    }
})
