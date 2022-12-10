package dev.jarand.oauthserver.account.authentication.resource

import dev.jarand.oauthserver.account.authentication.domain.AuthenticationError
import org.springframework.stereotype.Component

@Component
class AuthenticationErrorResourceAssembler {

    fun assemble(authenticationError: AuthenticationError): AuthenticationErrorResource {
        return AuthenticationErrorResource(authenticationError.error)
    }
}
