package dev.jarand.oauthserver.application.domain

import dev.jarand.oauthserver.application.controller.resource.CreateApplicationResource
import dev.jarand.oauthserver.config.service.IdService
import dev.jarand.oauthserver.config.service.TimeService
import dev.jarand.oauthserver.hash.HashService
import org.springframework.stereotype.Component
import java.util.*

@Component
class ApplicationAssembler(
    private val idService: IdService,
    private val timeService: TimeService,
    private val hashService: HashService
) {

    fun assembleNew(resource: CreateApplicationResource): Application {
        val created = timeService.applicationCreated()
        return Application(
            idService.applicationId(),
            resource.name,
            resource.description,
            resource.homePageUrl,
            resource.privacyPolicyUrl,
            idService.clientId().toString(),
            hashService.hash(idService.clientSecret().toString()),
            UUID.fromString(resource.ownerAccountId),
            created,
            created
        )
    }
}
