package dev.jarand.oauthserver.account.validation

import dev.jarand.oauthserver.account.controller.resource.CreateAccountResource
import dev.jarand.oauthserver.account.controller.resource.PasswordReset
import dev.jarand.oauthserver.account.validation.domain.ValidationError
import org.springframework.stereotype.Component

@Component
class AccountValidator {

    fun validate(resource: CreateAccountResource): List<ValidationError> {
        if (resource.password != resource.confirmedPassword) {
            return listOf(ValidationError("confirmedPassword", "Confirmed password does not match password"))
        }
        return emptyList()
    }

    fun validate(id: String): List<ValidationError> {
        if (!Regex("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}\$").matches(id)) {
            return listOf(ValidationError("id", "Invalid UUID"))
        }
        return emptyList()
    }

    fun validate(passwordReset: PasswordReset): List<ValidationError> {
        if (passwordReset.password != passwordReset.confirmedPassword) {
            return listOf(ValidationError("confirmedPassword", "Confirmed password does not match password"))
        }
        return emptyList()
    }
}
