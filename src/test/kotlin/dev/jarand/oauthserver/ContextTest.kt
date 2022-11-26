package dev.jarand.oauthserver

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    properties = [
        "spring.datasource.url=jdbc:h2:mem:oauth-server-test-db"
    ]
)
class ContextTest {

    @Test
    fun `Context loads`() {
    }
}
