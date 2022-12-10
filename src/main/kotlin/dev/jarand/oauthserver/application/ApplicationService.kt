package dev.jarand.oauthserver.application

import dev.jarand.oauthserver.application.domain.Application
import dev.jarand.oauthserver.application.repository.ApplicationRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class ApplicationService(private val applicationRepository: ApplicationRepository) {

    companion object {
        private val logger = LoggerFactory.getLogger(ApplicationService::class.java)
    }

    fun save(application: Application) {
        logger.debug("Saving application with id: ${application.id}")
        applicationRepository.save(application)
        logger.info("Saved application with id: ${application.id}")
    }

    fun get(id: UUID): Application? {
        logger.debug("Getting application with id: $id")
        val application = applicationRepository.get(id)
        if (application == null) {
            logger.debug("Got no application with id: $id")
            return null
        }
        logger.debug("Got application with id: $id")
        return application
    }

    fun delete(id: UUID) {
        logger.debug("Deleting application with id: $id")
        applicationRepository.delete(id)
        logger.info("Deleted application with id: $id")
    }
}
