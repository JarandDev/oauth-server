package dev.jarand.oauthserver.account.controller.resource

data class PasswordResetRequestResource(val id: String, val email: String, val token: String, val created: String)
