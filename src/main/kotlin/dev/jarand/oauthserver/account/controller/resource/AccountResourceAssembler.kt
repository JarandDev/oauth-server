package dev.jarand.oauthserver.account.controller.resource

import dev.jarand.oauthserver.account.domain.Account
import org.springframework.stereotype.Component

@Component
class AccountResourceAssembler {

    fun assemble(account: Account): AccountResource {
        return AccountResource(
            account.id.toString(),
            account.email,
            account.created.toString(),
            account.updated.toString()
        )
    }
}
