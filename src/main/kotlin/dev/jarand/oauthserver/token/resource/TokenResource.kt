package dev.jarand.oauthserver.token.resource

import com.fasterxml.jackson.annotation.JsonGetter

data class TokenResource(@get:JsonGetter("access_token") val accessToken: String, @get:JsonGetter("expires_in") val expiresIn: Long)
