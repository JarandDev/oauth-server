package dev.jarand.oauthserver.account.controller.resource

import jakarta.validation.constraints.NotEmpty

data class ResetPasswordResource(
    @NotEmpty val id: String,
    @NotEmpty val email: String,
    @NotEmpty val token: String,
    @NotEmpty val password: String,
    @NotEmpty val confirmedPassword: String
)
