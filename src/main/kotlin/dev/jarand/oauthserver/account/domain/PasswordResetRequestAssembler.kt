package dev.jarand.oauthserver.account.domain

import dev.jarand.oauthserver.account.controller.resource.RequestPasswordResetResource
import dev.jarand.oauthserver.config.service.IdService
import dev.jarand.oauthserver.config.service.TimeService
import dev.jarand.oauthserver.hash.HashService
import org.springframework.stereotype.Component

@Component
class PasswordResetRequestAssembler(
    private val idService: IdService,
    private val timeService: TimeService,
    private val hashService: HashService
) {

    fun assembleNew(resource: RequestPasswordResetResource): PasswordResetRequest {
        val token = idService.passwordResetToken().toString()
        return PasswordResetRequest(
            idService.passwordResetId(),
            resource.email,
            token,
            hashService.hash(token),
            timeService.passwordResetCreated()
        )
    }
}
