package dev.jarand.oauthserver.account.controller.resource

import jakarta.validation.constraints.NotEmpty

data class RequestPasswordResetResource(
    @NotEmpty val email: String
)
