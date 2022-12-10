package dev.jarand.oauthserver.token.domain

data class Token(val token: String, val expiresIn: Long)
