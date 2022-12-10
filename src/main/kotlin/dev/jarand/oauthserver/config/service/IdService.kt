package dev.jarand.oauthserver.config.service

import org.springframework.stereotype.Service
import java.util.*

@Service
class IdService(private val uuidSupplier: () -> UUID) {

    fun accountId(): UUID {
        return uuidSupplier.invoke()
    }

    fun passwordResetId(): UUID {
        return uuidSupplier.invoke()
    }

    fun passwordResetToken(): UUID {
        return uuidSupplier.invoke()
    }

    fun applicationId(): UUID {
        return uuidSupplier.invoke()
    }

    fun clientId(): UUID {
        return uuidSupplier.invoke()
    }

    fun clientSecret(): UUID {
        return uuidSupplier.invoke()
    }
}
