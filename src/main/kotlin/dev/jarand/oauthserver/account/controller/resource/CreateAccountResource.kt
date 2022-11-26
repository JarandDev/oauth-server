package dev.jarand.oauthserver.account.controller.resource

import jakarta.validation.constraints.NotEmpty

data class CreateAccountResource(
    @NotEmpty val email: String,
    @NotEmpty val password: String,
    @NotEmpty val confirmedPassword: String
)
