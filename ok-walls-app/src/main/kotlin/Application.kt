package ru.otus.kotlin.walls.app

import com.auth0.jwt.JWT
import io.ktor.serialization.jackson.jackson
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTCredential
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.plugins.callloging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.defaultheaders.DefaultHeaders
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import org.slf4j.event.Level
import ru.otus.kotlin.walls.api.v1.apiV1Mapper
import ru.otus.kotlin.walls.app.AuthConfig.Companion.GROUPS_CLAIM
import ru.otus.kotlin.walls.app.base.resolveAlgorithm
import ru.otus.kotlin.walls.app.v1.v1

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.prepare(settings: AppSettings = initAppSettings()) {

    install(DefaultHeaders)

    install(CallLogging) {
        level = Level.INFO
    }

    install(Authentication) {
        jwt("auth-jwt") {
            val authConfig = settings.auth
            realm = authConfig.realm

            verifier {
                val algorithm = it.resolveAlgorithm(authConfig)
                JWT
                    .require(algorithm)
                    .withAudience(authConfig.audience)
                    .withIssuer(authConfig.issuer)
                    .build()
            }
            validate { jwtCredential: JWTCredential ->
                when {
                    jwtCredential.payload.getClaim(GROUPS_CLAIM).asList(String::class.java).isNullOrEmpty() -> null

                    else -> JWTPrincipal(jwtCredential.payload)
                }
            }
        }
    }

    routing {
        route("v1") {
            install(ContentNegotiation) {
                jackson {
                    setConfig(apiV1Mapper.serializationConfig)
                    setConfig(apiV1Mapper.deserializationConfig)
                }
            }

            authenticate("auth-jwt") {
                v1(settings.processor)
            }
        }
    }
}
