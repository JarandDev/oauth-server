package dev.jarand.oauthserver.token.resource

import dev.jarand.oauthserver.token.domain.Token
import org.springframework.stereotype.Component

@Component
class TokenResourceAssembler {

    fun assemble(token: Token): TokenResource {
        return TokenResource(token.token, token.expiresIn)
    }
}
