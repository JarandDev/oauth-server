package dev.jarand.oauthserver.application.controller

import dev.jarand.oauthserver.application.ApplicationService
import dev.jarand.oauthserver.application.controller.resource.ApplicationResource
import dev.jarand.oauthserver.application.controller.resource.ApplicationResourceAssembler
import dev.jarand.oauthserver.application.controller.resource.CreateApplicationResource
import dev.jarand.oauthserver.application.domain.ApplicationAssembler
import dev.jarand.oauthserver.validation.ControllerValidator
import dev.jarand.oauthserver.validation.resource.ValidationErrorResourceAssembler
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("application")
class ApplicationController(
    private val applicationService: ApplicationService,
    private val applicationAssembler: ApplicationAssembler,
    private val applicationResourceAssembler: ApplicationResourceAssembler,
    private val controllerValidator: ControllerValidator,
    private val validationErrorResourceAssembler: ValidationErrorResourceAssembler
) {

    @PostMapping
    fun createApplication(@Valid @RequestBody resource: CreateApplicationResource): ResponseEntity<ApplicationResource> {
        val application = applicationAssembler.assembleNew(resource)
        applicationService.save(application)
        return ResponseEntity.ok(applicationResourceAssembler.assemble(application))
    }

    @GetMapping("{id}")
    fun getApplication(@PathVariable id: String): ResponseEntity<Any> {
        val errors = controllerValidator.validate(id)
        if (errors.isNotEmpty()) {
            return ResponseEntity.badRequest().body(validationErrorResourceAssembler.assemble(errors))
        }
        val account = applicationService.get(UUID.fromString(id)) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(applicationResourceAssembler.assemble(account))
    }

    @DeleteMapping("{id}")
    fun deleteApplication(@PathVariable id: String): ResponseEntity<Any> {
        val errors = controllerValidator.validate(id)
        if (errors.isNotEmpty()) {
            return ResponseEntity.badRequest().body(validationErrorResourceAssembler.assemble(errors))
        }
        val applicationId = UUID.fromString(id)
        applicationService.get(applicationId) ?: return ResponseEntity.notFound().build()
        applicationService.delete(applicationId)
        return ResponseEntity.noContent().build()
    }
}
