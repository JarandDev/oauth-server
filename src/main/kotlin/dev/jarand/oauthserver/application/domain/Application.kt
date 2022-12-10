package dev.jarand.oauthserver.application.domain

import dev.jarand.oauthserver.hash.HashedText
import java.time.Instant
import java.util.*

data class Application(
    val id: UUID,
    val name: String,
    val description: String,
    val homePageUrl: String,
    val privacyPolicyUrl: String,
    val clientId: String,
    val clientSecret: HashedText,
    val ownerAccountId: UUID,
    val created: Instant,
    val updated: Instant
)
