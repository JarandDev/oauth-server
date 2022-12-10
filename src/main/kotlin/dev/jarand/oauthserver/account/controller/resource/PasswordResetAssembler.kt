package dev.jarand.oauthserver.account.controller.resource

import org.springframework.stereotype.Component
import java.util.*

@Component
class PasswordResetAssembler {

    fun assemble(resource: ResetPasswordResource): PasswordReset {
        return PasswordReset(
            UUID.fromString(resource.id),
            resource.email,
            resource.token,
            resource.password,
            resource.confirmedPassword
        )
    }
}
