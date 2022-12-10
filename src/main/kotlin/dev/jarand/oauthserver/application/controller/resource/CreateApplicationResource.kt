package dev.jarand.oauthserver.application.controller.resource

import jakarta.validation.constraints.NotEmpty

data class CreateApplicationResource(
    @NotEmpty val name: String,
    @NotEmpty val description: String,
    @NotEmpty val homePageUrl: String,
    @NotEmpty val privacyPolicyUrl: String,
    @NotEmpty val ownerAccountId: String
)
