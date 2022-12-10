package dev.jarand.oauthserver.application.controller.resource

import dev.jarand.oauthserver.application.domain.Application
import org.springframework.stereotype.Component

@Component
class ApplicationResourceAssembler {

    fun assemble(application: Application): ApplicationResource {
        return ApplicationResource(
            application.id.toString(),
            application.name,
            application.description,
            application.homePageUrl,
            application.privacyPolicyUrl,
            application.clientId,
            application.ownerAccountId.toString(),
            application.created.toString(),
            application.updated.toString()
        )
    }
}
