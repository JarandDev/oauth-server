package dev.jarand.oauthserver.account.domain

import dev.jarand.oauthserver.hash.HashedText
import java.time.Instant
import java.util.*

data class PasswordResetRequest(val id: UUID, val email: String, val plainTextToken: String, val token: HashedText, val created: Instant)
