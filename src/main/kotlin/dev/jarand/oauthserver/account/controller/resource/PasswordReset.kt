package dev.jarand.oauthserver.account.controller.resource

import java.util.*

data class PasswordReset(val id: UUID, val email: String, val token: String, val password: String, val confirmedPassword: String)
