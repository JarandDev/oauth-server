package dev.jarand.oauthserver.account.domain

import dev.jarand.oauthserver.account.controller.resource.CreateAccountResource
import dev.jarand.oauthserver.config.service.IdService
import dev.jarand.oauthserver.config.service.TimeService
import dev.jarand.oauthserver.hash.HashService
import org.springframework.stereotype.Component

@Component
class AccountAssembler(
    private val idService: IdService,
    private val timeService: TimeService,
    private val hashService: HashService
) {

    fun assembleNew(resource: CreateAccountResource): Account {
        val created = timeService.accountCreated()
        return Account(
            idService.accountId(),
            resource.email,
            hashService.hash(resource.password),
            created,
            created
        )
    }
}
