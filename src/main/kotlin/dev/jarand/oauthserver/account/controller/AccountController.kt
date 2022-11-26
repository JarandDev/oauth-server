package dev.jarand.oauthserver.account.controller

import dev.jarand.oauthserver.account.controller.resource.*
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("account")
class AccountController {

    @PostMapping
    fun createAccount(@Valid @RequestBody resource: CreateAccountResource): ResponseEntity<Any> {
        return ResponseEntity.ok().build()
    }

    @GetMapping("{id}")
    fun getAccount(@PathVariable id: String): ResponseEntity<AccountResource> {
        return ResponseEntity.ok().build()
    }

    @PostMapping("authenticate")
    fun authenticateAccount(@Valid @RequestBody resource: AuthenticateAccountResource): ResponseEntity<Any> {
        return ResponseEntity.ok().build()
    }

    @PostMapping("request-password-reset")
    fun requestPasswordReset(@Valid @RequestBody resource: RequestPasswordResetResource): ResponseEntity<Any> {
        return ResponseEntity.ok().build()
    }

    @PostMapping("reset-password")
    fun resetPassword(@Valid @RequestBody resource: ResetPasswordResource): ResponseEntity<Any> {
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("{id}")
    fun deleteAccount(@PathVariable id: String): ResponseEntity<Any> {
        return ResponseEntity.noContent().build()
    }
}
