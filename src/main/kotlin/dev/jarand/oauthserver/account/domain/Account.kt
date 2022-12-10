package dev.jarand.oauthserver.account.domain

import dev.jarand.oauthserver.hash.HashedText
import java.time.Instant
import java.util.*

data class Account(
    val id: UUID,
    val email: String,
    val password: HashedText,
    val created: Instant,
    val updated: Instant
)
