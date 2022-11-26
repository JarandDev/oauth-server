package dev.jarand.oauthserver.account.controller.resource

import jakarta.validation.constraints.NotEmpty

data class ResetPasswordResource(
    @NotEmpty val resetPasswordToken: String,
    @NotEmpty val password: String,
    @NotEmpty val confirmedPassword: String
)
