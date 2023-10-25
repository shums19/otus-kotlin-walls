package ru.otus.kotlin.walls.app

data class AuthConfig(
    val secret: String,
    val issuer: String,
    val audience: String,
    val realm: String,
    val clientId: String,
    val certUrl: String? = null,
) {
    companion object {
        const val ID_CLAIM = "sub"
        const val GROUPS_CLAIM = "groups"
        const val F_NAME_CLAIM = "fname"
        const val M_NAME_CLAIM = "mname"
        const val L_NAME_CLAIM = "lname"

        val TEST = AuthConfig(
            secret = "secret",
            issuer = "walls",
            audience = "ad-users",
            realm = "ok-walls",
            clientId = "ok-walls-service",
        )

        val NONE = AuthConfig(
            secret = "",
            issuer = "",
            audience = "",
            realm = "",
            clientId = "",
        )
    }
}
