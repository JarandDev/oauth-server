package dev.jarand.oauthserver.account.controller.resource

import jakarta.validation.constraints.NotEmpty

data class AuthenticateAccountResource(
    @NotEmpty val email: String,
    @NotEmpty val password: String
)
