package ru.otus.kotlin.walls.app.stubs

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.jackson.jackson
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import ru.otus.kotlin.walls.api.v1.models.AdCreateObject
import ru.otus.kotlin.walls.api.v1.models.AdCreateRequest
import ru.otus.kotlin.walls.api.v1.models.AdCreateResponse
import ru.otus.kotlin.walls.api.v1.models.AdDebug
import ru.otus.kotlin.walls.api.v1.models.AdDeleteObject
import ru.otus.kotlin.walls.api.v1.models.AdDeleteRequest
import ru.otus.kotlin.walls.api.v1.models.AdDeleteResponse
import ru.otus.kotlin.walls.api.v1.models.AdPermissions
import ru.otus.kotlin.walls.api.v1.models.AdReadObject
import ru.otus.kotlin.walls.api.v1.models.AdReadRequest
import ru.otus.kotlin.walls.api.v1.models.AdReadResponse
import ru.otus.kotlin.walls.api.v1.models.AdRequestDebugMode
import ru.otus.kotlin.walls.api.v1.models.AdRequestDebugStub
import ru.otus.kotlin.walls.api.v1.models.AdResponseObject
import ru.otus.kotlin.walls.api.v1.models.AdSearchFilter
import ru.otus.kotlin.walls.api.v1.models.AdSearchRequest
import ru.otus.kotlin.walls.api.v1.models.AdSearchResponse
import ru.otus.kotlin.walls.api.v1.models.AdUpdateObject
import ru.otus.kotlin.walls.api.v1.models.AdUpdateRequest
import ru.otus.kotlin.walls.api.v1.models.AdUpdateResponse
import ru.otus.kotlin.walls.api.v1.models.BuildingType
import ru.otus.kotlin.walls.api.v1.models.ResponseResult
import ru.otus.kotlin.walls.api.v1.models.Status
import ru.otus.kotlin.walls.api.v1.models.Type

class V1ApiStubTest : FreeSpec({
    val expectedResponseAd = AdResponseObject(
        id = "5ab73e69-c92f-4bc9-8f7a-4b483ae87b0a",
        ownerId = "51f51c82-049d-45b7-9e41-68fc0121c90f",
        title = "3-комнатная квартира",
        description = "Квартира в центре",
        active = true,
        hasLift = true,
        area = 60.5.toBigDecimal(),
        price = 3000000.toBigDecimal(),
        roomsNumber = 3,
        floor = 4,
        type = Type.APARTMENT,
        status = Status.NEW,
        buildingType = BuildingType.BRICK,
        lock = "e08d0ed8-7270-11ee-b962-0242ac120002",
        permissions = setOf(
            AdPermissions.DELETE,
            AdPermissions.MAKE_VISIBLE_PUBLIC,
            AdPermissions.UPDATE,
            AdPermissions.MAKE_VISIBLE_OWN,
            AdPermissions.MAKE_VISIBLE_GROUP,
            AdPermissions.READ,
        ),
    )

    fun ApplicationTestBuilder.prepareClient() = createClient {
        install(ContentNegotiation) {
            jackson {
                disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

                enable(SerializationFeature.INDENT_OUTPUT)
                writerWithDefaultPrettyPrinter()
            }
        }
    }

    "create" {
        testApplication {
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
                contentType(ContentType.Application.Json)
                setBody(requestObj)
            }
            val responseObj = response.body<AdCreateResponse>()

            response.status.value shouldBe 200
            responseObj shouldBe AdCreateResponse(
                requestId = "12345",
                result = ResponseResult.SUCCESS,
                ad = expectedResponseAd.copy(
                    title = "title",
                    description = "desc",
                    area = 52.toBigDecimal(),
                    price = 3250000.toBigDecimal(),
                ),
            )
        }
    }

    "read" {
        testApplication {
            val client = prepareClient()

            val response = client.post("/v1/ad/read") {
                val requestObj = AdReadRequest(
                    requestId = "12345",
                    ad = AdReadObject(id = "5ab73e69-c92f-4bc9-8f7a-4b483ae87b0a"),
                    debug = AdDebug(
                        mode = AdRequestDebugMode.STUB,
                        stub = AdRequestDebugStub.SUCCESS
                    )
                )
                contentType(ContentType.Application.Json)
                setBody(requestObj)
            }
            val responseObj = response.body<AdReadResponse>()

            response.status.value shouldBe 200
            responseObj shouldBe AdReadResponse(
                requestId = "12345",
                result = ResponseResult.SUCCESS,
                ad = expectedResponseAd,
            )
        }
    }

    "update" {
        testApplication {
            val client = prepareClient()

            val response = client.post("/v1/ad/update") {
                val requestObj = AdUpdateRequest(
                    requestId = "12345",
                    ad = AdUpdateObject(
                        id = "5ab73e69-c92f-4bc9-8f7a-4b483ae87b0a",
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
                    debug = AdDebug(
                        mode = AdRequestDebugMode.STUB,
                        stub = AdRequestDebugStub.SUCCESS
                    )
                )
                contentType(ContentType.Application.Json)
                setBody(requestObj)
            }
            val responseObj = response.body<AdUpdateResponse>()

            response.status.value shouldBe 200
            responseObj shouldBe AdUpdateResponse(
                requestId = "12345",
                result = ResponseResult.SUCCESS,
                ad = expectedResponseAd.copy(
                    title = "title",
                    description = "desc",
                    area = 52.toBigDecimal(),
                    price = 3250000.toBigDecimal(),
                ),
            )
        }
    }

    "delete" {
        testApplication {
            val client = prepareClient()

            val response = client.post("/v1/ad/delete") {
                val requestObj = AdDeleteRequest(
                    requestId = "12345",
                    ad = AdDeleteObject(
                        id = "5ab73e69-c92f-4bc9-8f7a-4b483ae87b0a",
                    ),
                    debug = AdDebug(
                        mode = AdRequestDebugMode.STUB,
                        stub = AdRequestDebugStub.SUCCESS
                    )
                )
                contentType(ContentType.Application.Json)
                setBody(requestObj)
            }
            val responseObj = response.body<AdDeleteResponse>()

            response.status.value shouldBe 200
            responseObj shouldBe AdDeleteResponse(
                requestId = "12345",
                result = ResponseResult.SUCCESS,
                ad = expectedResponseAd,
            )
        }
    }

    "search" {
        testApplication {
            val client = prepareClient()

            val response = client.post("/v1/ad/search") {
                val requestObj = AdSearchRequest(
                    requestId = "12345",
                    adFilter = AdSearchFilter("квартира"),
                    debug = AdDebug(
                        mode = AdRequestDebugMode.STUB,
                        stub = AdRequestDebugStub.SUCCESS
                    )
                )
                contentType(ContentType.Application.Json)
                setBody(requestObj)
            }
            val responseObj = response.body<AdSearchResponse>()

            response.status.value shouldBe 200
            responseObj shouldBe AdSearchResponse(
                requestId = "12345",
                ads = listOf(
                    expectedResponseAd.copy(
                        id = "5ab73e69-c92f-4bc9-8f7a-4b483ae87b0a",
                        title = "квартира 5ab73e69-c92f-4bc9-8f7a-4b483ae87b0a",
                        description = "desc квартира 5ab73e69-c92f-4bc9-8f7a-4b483ae87b0a",
                    ),
                    expectedResponseAd.copy(
                        id = "51f51c82-049d-45b7-9e41-68fc0121c90f",
                        title = "квартира 51f51c82-049d-45b7-9e41-68fc0121c90f",
                        description = "desc квартира 51f51c82-049d-45b7-9e41-68fc0121c90f",
                    ),
                    expectedResponseAd.copy(
                        id = "8b32d0fe-6527-11ee-8c99-0242ac120002",
                        title = "квартира 8b32d0fe-6527-11ee-8c99-0242ac120002",
                        description = "desc квартира 8b32d0fe-6527-11ee-8c99-0242ac120002",
                    ),
                    expectedResponseAd.copy(
                        id = "96cd781a-6527-11ee-8c99-0242ac120002",
                        title = "квартира 96cd781a-6527-11ee-8c99-0242ac120002",
                        description = "desc квартира 96cd781a-6527-11ee-8c99-0242ac120002",
                    ),
                    expectedResponseAd.copy(
                        id = "276004ea-b726-4a9b-bec7-37b8bb0852e6",
                        title = "квартира 276004ea-b726-4a9b-bec7-37b8bb0852e6",
                        description = "desc квартира 276004ea-b726-4a9b-bec7-37b8bb0852e6",
                    ),
                ),
                result = ResponseResult.SUCCESS,
            )
        }
    }
})
