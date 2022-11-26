package dev.jarand.oauthserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ApplicationStarter

fun main(args: Array<String>) {
    runApplication<ApplicationStarter>(*args)
}
