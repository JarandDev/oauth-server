package dev.jarand.oauthserver.account.controller

import dev.jarand.oauthserver.account.AccountService
import dev.jarand.oauthserver.account.authentication.AccountAuthenticator
import dev.jarand.oauthserver.account.authentication.resource.AuthenticationErrorResourceAssembler
import dev.jarand.oauthserver.account.controller.resource.*
import dev.jarand.oauthserver.account.domain.AccountAssembler
import dev.jarand.oauthserver.account.domain.PasswordResetRequestAssembler
import dev.jarand.oauthserver.hash.HashService
import dev.jarand.oauthserver.token.TokenService
import dev.jarand.oauthserver.token.resource.TokenResourceAssembler
import dev.jarand.oauthserver.validation.ControllerValidator
import dev.jarand.oauthserver.validation.domain.ValidationError
import dev.jarand.oauthserver.validation.resource.ValidationErrorResourceAssembler
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("account")
class AccountController(
    private val accountService: AccountService,
    private val accountAuthenticator: AccountAuthenticator,
    private val controllerValidator: ControllerValidator,
    private val accountAssembler: AccountAssembler,
    private val accountResourceAssembler: AccountResourceAssembler,
    private val passwordResetRequestAssembler: PasswordResetRequestAssembler,
    private val passwordResetRequestResourceAssembler: PasswordResetRequestResourceAssembler,
    private val passwordResetAssembler: PasswordResetAssembler,
    private val authenticationErrorResourceAssembler: AuthenticationErrorResourceAssembler,
    private val validationErrorResourceAssembler: ValidationErrorResourceAssembler,
    private val hashService: HashService,
    private val tokenService: TokenService,
    private val tokenResourceAssembler: TokenResourceAssembler
) {

    @PostMapping
    fun createAccount(@Valid @RequestBody resource: CreateAccountResource): ResponseEntity<Any> {
        val errors = controllerValidator.validate(resource)
        if (errors.isNotEmpty()) {
            return ResponseEntity.badRequest().body(validationErrorResourceAssembler.assemble(errors))
        }
        val account = accountAssembler.assembleNew(resource)
        accountService.save(account)
        return ResponseEntity.ok(accountResourceAssembler.assemble(account))
    }

    @GetMapping("{id}")
    fun getAccount(@PathVariable id: String): ResponseEntity<Any> {
        val errors = controllerValidator.validate(id)
        if (errors.isNotEmpty()) {
            return ResponseEntity.badRequest().body(validationErrorResourceAssembler.assemble(errors))
        }
        val account = accountService.get(UUID.fromString(id)) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(accountResourceAssembler.assemble(account))
    }

    @PostMapping("authenticate")
    fun authenticateAccount(@Valid @RequestBody resource: AuthenticateAccountResource): ResponseEntity<Any> {
        val (account, errors) = accountAuthenticator.authenticate(resource)
        if (errors != null && account == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authenticationErrorResourceAssembler.assemble(errors))
        }
        val accountId = account?.id ?: throw IllegalStateException()
        val token = tokenService.createTokenForAccount(accountId)
        return ResponseEntity.ok(tokenResourceAssembler.assemble(token))
    }

    @PostMapping("request-password-reset")
    fun requestPasswordReset(@Valid @RequestBody resource: RequestPasswordResetResource): ResponseEntity<PasswordResetRequestResource> {
        val passwordResetRequest = passwordResetRequestAssembler.assembleNew(resource)
        // TODO: Send email with password reset token
        accountService.save(passwordResetRequest)
        return ResponseEntity.ok(passwordResetRequestResourceAssembler.assemble(passwordResetRequest))
    }

    @PostMapping("reset-password")
    fun resetPassword(@Valid @RequestBody resource: ResetPasswordResource): ResponseEntity<Any> {
        val passwordReset = passwordResetAssembler.assemble(resource)
        val passwordResetRequest = accountService.get(passwordReset.id, passwordReset.email) ?: return ResponseEntity.notFound().build()
        val tokenResult = accountAuthenticator.authenticatePasswordReset(passwordReset, passwordResetRequest)
        if (tokenResult != null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authenticationErrorResourceAssembler.assemble(tokenResult))
        }
        val account = accountService.get(passwordReset.email) ?: return ResponseEntity.badRequest().body(validationErrorResourceAssembler.assemble(listOf(ValidationError("email", "No account found"))))
        val passwordErrors = controllerValidator.validate(passwordReset)
        if (passwordErrors.isNotEmpty()) {
            return ResponseEntity.badRequest().body(validationErrorResourceAssembler.assemble(passwordErrors))
        }
        accountService.updatePassword(hashService.hash(passwordReset.password), account)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("{id}")
    fun deleteAccount(@PathVariable id: String): ResponseEntity<Any> {
        val errors = controllerValidator.validate(id)
        if (errors.isNotEmpty()) {
            return ResponseEntity.badRequest().body(validationErrorResourceAssembler.assemble(errors))
        }
        val accountId = UUID.fromString(id)
        accountService.get(accountId) ?: return ResponseEntity.notFound().build()
        accountService.delete(accountId)
        return ResponseEntity.noContent().build()
    }
}
