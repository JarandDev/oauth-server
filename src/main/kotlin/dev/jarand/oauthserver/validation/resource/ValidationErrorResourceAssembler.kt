package dev.jarand.oauthserver.validation.resource

import dev.jarand.oauthserver.validation.domain.ValidationError
import org.springframework.stereotype.Component

@Component
class ValidationErrorResourceAssembler {

    fun assemble(validationErrors: List<ValidationError>): List<ValidationErrorResource> {
        return validationErrors.map { assemble(it) }
    }

    private fun assemble(validationError: ValidationError): ValidationErrorResource {
        return ValidationErrorResource(validationError.field, validationError.error)
    }
}
