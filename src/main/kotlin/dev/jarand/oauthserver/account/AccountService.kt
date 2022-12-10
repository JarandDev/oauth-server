package dev.jarand.oauthserver.account

import dev.jarand.oauthserver.account.domain.Account
import dev.jarand.oauthserver.account.domain.PasswordResetRequest
import dev.jarand.oauthserver.account.repository.AccountRepository
import dev.jarand.oauthserver.hash.HashedText
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class AccountService(private val accountRepository: AccountRepository) {

    companion object {
        private val logger = LoggerFactory.getLogger(AccountService::class.java)
    }

    fun save(account: Account) {
        logger.debug("Saving account with id: ${account.id}")
        accountRepository.save(account)
        logger.info("Saved account with id: ${account.id}")
    }

    fun updatePassword(password: HashedText, account: Account) {
        logger.debug("Updating password of account with id: ${account.id}")
        accountRepository.updatePassword(password, account)
        logger.info("Updated password of account with id: ${account.id}")
    }

    fun get(id: UUID): Account? {
        logger.debug("Getting account with id: $id")
        val account = accountRepository.get(id)
        if (account == null) {
            logger.debug("Got no account with id: $id")
            return null
        }
        logger.debug("Got account with id: $id")
        return account
    }

    fun get(email: String): Account? {
        logger.debug("Getting account with email: $email")
        val account = accountRepository.get(email)
        if (account == null) {
            logger.debug("Got no account with email: $email")
            return null
        }
        logger.debug("Got account with email: $email (id: ${account.id})")
        return account
    }

    fun delete(id: UUID) {
        logger.debug("Deleting account with id: $id")
        accountRepository.delete(id)
        logger.info("Deleted account with id: $id")
    }

    fun save(passwordResetRequest: PasswordResetRequest) {
        logger.debug("Saving password reset with id: ${passwordResetRequest.id}")
        accountRepository.save(passwordResetRequest)
        logger.info("Saved password reset with id: ${passwordResetRequest.id}")
    }

    fun get(id: UUID, email: String): PasswordResetRequest? {
        logger.debug("Getting password reset request with id: $id and email: $email")
        val passwordResetRequest = accountRepository.get(id, email)
        if (passwordResetRequest == null) {
            logger.debug("Got no password reset request with id: $id and email: $email")
            return null
        }
        logger.debug("Got password reset request with id: $id and email: $email")
        return passwordResetRequest
    }
}
