package dev.jarand.oauthserver.hash

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class HashServiceTest {

    lateinit var hashService: HashService

    @BeforeEach
    fun setup() {
        hashService = HashService()
    }

    @Test
    fun `hashed password should match the same password`() {
        val password = "somePassword"

        val hashedText = hashService.hash(password)
        val matches = hashService.matches(password, hashedText)

        assertThat(matches).isTrue
    }

    @Test
    fun `hashed password should not match another password`() {
        val password = "somePassword"
        val otherPassword = "otherPassword"

        val hashedText = hashService.hash(password)
        val matches = hashService.matches(otherPassword, hashedText)

        assertThat(matches).isFalse
    }
}
