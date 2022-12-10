package dev.jarand.oauthserver.config.service

import org.springframework.stereotype.Service
import java.time.Instant

@Service
class TimeService(private val instantSupplier: () -> Instant) {

    fun accountCreated(): Instant {
        return instantSupplier.invoke()
    }

    fun accountUpdated(): Instant {
        return instantSupplier.invoke()
    }

    fun passwordResetCreated(): Instant {
        return instantSupplier.invoke()
    }

    fun applicationCreated(): Instant {
        return instantSupplier.invoke()
    }

    fun tokenIssuedAt(): Instant {
        return instantSupplier.invoke()
    }
}
