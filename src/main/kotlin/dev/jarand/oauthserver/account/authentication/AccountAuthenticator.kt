package dev.jarand.oauthserver.account.authentication

import dev.jarand.oauthserver.account.AccountService
import dev.jarand.oauthserver.account.authentication.domain.AuthenticationError
import dev.jarand.oauthserver.account.controller.resource.AuthenticateAccountResource
import dev.jarand.oauthserver.account.controller.resource.PasswordReset
import dev.jarand.oauthserver.account.domain.Account
import dev.jarand.oauthserver.account.domain.PasswordResetRequest
import dev.jarand.oauthserver.hash.HashService
import org.springframework.stereotype.Component

@Component
class AccountAuthenticator(
    private val accountService: AccountService, private val hashService: HashService
) {

    fun authenticate(resource: AuthenticateAccountResource): Pair<Account?, AuthenticationError?> {
        val account = accountService.get(resource.email) ?: return Pair(null, error())
        if (!hashService.matches(resource.password, account.password)) {
            return Pair(null, error())
        }
        return Pair(account, null)
    }

    fun authenticatePasswordReset(passwordReset: PasswordReset, passwordResetRequest: PasswordResetRequest): AuthenticationError? {
        if (!hashService.matches(passwordReset.token, passwordResetRequest.token)) {
            return AuthenticationError("Invalid token")
        }
        return null
    }

    private fun error(): AuthenticationError {
        return AuthenticationError("Account not found or incorrect password")
    }
}
