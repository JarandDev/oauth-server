package dev.jarand.oauthserver.account.controller.resource

import dev.jarand.oauthserver.account.domain.PasswordResetRequest
import org.springframework.stereotype.Component

@Component
class PasswordResetRequestResourceAssembler {

    fun assemble(passwordResetRequest: PasswordResetRequest): PasswordResetRequestResource {
        return PasswordResetRequestResource(
            passwordResetRequest.id.toString(),
            passwordResetRequest.email,
            passwordResetRequest.plainTextToken,
            passwordResetRequest.created.toString()
        )
    }
}
