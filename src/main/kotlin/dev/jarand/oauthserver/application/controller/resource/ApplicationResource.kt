package dev.jarand.oauthserver.application.controller.resource

data class ApplicationResource(
    val id: String,
    val name: String,
    val description: String,
    val homePageUrl: String,
    val privacyPolicyUrl: String,
    val clientId: String,
    val ownerAccountId: String,
    val created: String,
    val updated: String
)
