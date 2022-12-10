package dev.jarand.oauthserver.utility

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import dev.jarand.oauthserver.config.service.IdService
import dev.jarand.oauthserver.config.service.TimeService
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.transaction.annotation.Transactional

@ExtendWith(SpringExtension::class)
@SpringBootTest(
    properties = [
        "spring.datasource.url=jdbc:h2:mem:oauth-server-test-db",
        "logging.level.dev.jarand.oauthserver=DEBUG"
    ]
)
@AutoConfigureMockMvc
@Transactional
class ComponentTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @MockkBean
    lateinit var idService: IdService

    @MockkBean
    lateinit var timeService: TimeService
}
