package dev.jarand.oauthserver.token

import dev.jarand.oauthserver.config.service.IdService
import dev.jarand.oauthserver.config.service.TimeService
import dev.jarand.oauthserver.token.domain.Token
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.security.PrivateKey
import java.util.*

@Service
class TokenService(
    private val idService: IdService,
    private val timeService: TimeService,
    private val privateKey: PrivateKey,
    @Value("\${token.issuer}") private val issuer: String,
    @Value("\${token.audience.account}") private val accountAudience: String,
    @Value("\${token.lifetime.seconds}") private val lifetimeSeconds: Long
) {

    fun createTokenForAccount(accountId: UUID): Token {
        val issuedAt = timeService.tokenIssuedAt()
        val token = Jwts.builder()
            .setId(idService.tokenId().toString())
            .setSubject(accountId.toString())
            .setIssuer(issuer)
            .setAudience(accountAudience)
            .setIssuedAt(Date.from(issuedAt))
            .setExpiration(Date.from(issuedAt.plusSeconds(lifetimeSeconds)))
            .signWith(privateKey, SignatureAlgorithm.ES512)
            .compact()
        return Token(token, lifetimeSeconds)
    }
}
