package dev.jarand.oauthserver

import com.fasterxml.jackson.module.kotlin.readValue
import dev.jarand.oauthserver.token.resource.TokenResource
import dev.jarand.oauthserver.utility.ComponentTest
import dev.jarand.oauthserver.utility.fileAsString
import io.mockk.every
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.Instant
import java.util.*

class OAuthServerComponentTest : ComponentTest() {

    @Test
    fun `Creating account should return expected account when retrieving`() {
        every { idService.accountId() } returns UUID.fromString("e3fa83cb-9df9-49e7-a37a-a5eb30ea90d1")
        every { timeService.accountCreated() } returns Instant.parse("2022-11-28T18:56:59.560966600Z")

        mockMvc.perform(
            post("/account").content(fileAsString("/json/account/create/POST-account.json")).contentType(APPLICATION_JSON)
        ).andExpect(status().isOk).andExpect(content().json(fileAsString("/json/account/create/POST-and-GET-account-response.json"), true))

        mockMvc.perform(
            get("/account/e3fa83cb-9df9-49e7-a37a-a5eb30ea90d1")
        ).andExpect(status().isOk).andExpect(content().json(fileAsString("/json/account/create/POST-and-GET-account-response.json"), true))
    }

    @Test
    fun `Deleting account should return expected account when retrieving`() {
        every { idService.accountId() } returns UUID.fromString("895bee3c-f2bf-41ad-babe-38a8b466b635")
        every { timeService.accountCreated() } returns Instant.parse("2022-11-29T21:05:14.560966600Z")

        mockMvc.perform(
            post("/account").content(fileAsString("/json/account/delete/POST-account.json")).contentType(APPLICATION_JSON)
        ).andExpect(status().isOk).andExpect(content().json(fileAsString("/json/account/delete/POST-and-GET-account-response.json"), true))

        mockMvc.perform(
            get("/account/895bee3c-f2bf-41ad-babe-38a8b466b635")
        ).andExpect(status().isOk).andExpect(content().json(fileAsString("/json/account/delete/POST-and-GET-account-response.json"), true))

        mockMvc.perform(
            delete("/account/895bee3c-f2bf-41ad-babe-38a8b466b635")
        ).andExpect(status().isNoContent)

        mockMvc.perform(
            get("/account/895bee3c-f2bf-41ad-babe-38a8b466b635")
        ).andExpect(status().isNotFound)

        mockMvc.perform(
            delete("/account/895bee3c-f2bf-41ad-babe-38a8b466b635")
        ).andExpect(status().isNotFound)
    }

    @Test
    fun `Creating and resetting password of account should return expected result when authenticating`() {
        every { idService.accountId() } returns UUID.fromString("7d09e316-c59d-4fc8-a203-146cd394f7c2")
        every { idService.passwordResetId() } returns UUID.fromString("52c705fe-174b-4744-b978-2b8501ec22ca")
        every { idService.passwordResetToken() } returns UUID.fromString("1198f5c3-7cf8-4349-b771-41b9a4909c92")
        every { idService.tokenId() } returns UUID.fromString("f0c55dfe-ac44-46b7-9579-c464a169c4b9") andThen UUID.fromString("148c207a-1b7c-4d81-9f04-3b4e06b0cba6")
        every { timeService.accountCreated() } returns Instant.parse("2022-12-10T18:56:59.560966600Z")
        every { timeService.accountUpdated() } returns Instant.parse("2022-12-10T19:57:03.560966600Z")
        every { timeService.passwordResetCreated() } returns Instant.parse("2022-12-11T20:03:07.560966600Z")
        every { timeService.tokenIssuedAt() } returns Instant.parse("2022-11-10T18:41:59.560966600Z") andThen Instant.parse("2022-11-10T18:42:59.560966600Z")

        mockMvc.perform(
            post("/account").content(fileAsString("/json/account/password-reset/POST-account.json")).contentType(APPLICATION_JSON)
        ).andExpect(status().isOk).andExpect(content().json(fileAsString("/json/account/password-reset/POST-and-GET-account-response.json"), true))

        mockMvc.perform(
            get("/account/7d09e316-c59d-4fc8-a203-146cd394f7c2")
        ).andExpect(status().isOk).andExpect(content().json(fileAsString("/json/account/password-reset/POST-and-GET-account-response.json"), true))

        mockMvc.perform(
            post("/account/authenticate").content(fileAsString("/json/account/password-reset/POST-authenticate.json")).contentType(APPLICATION_JSON)
        ).andExpect(status().isOk)

        mockMvc.perform(
            post("/account/request-password-reset").content(fileAsString("/json/account/password-reset/POST-request-password-reset.json")).contentType(APPLICATION_JSON)
        ).andExpect(status().isOk).andExpect(content().json(fileAsString("/json/account/password-reset/POST-request-password-reset-response.json"), true))

        mockMvc.perform(
            post("/account/reset-password").content(fileAsString("/json/account/password-reset/POST-reset-password.json")).contentType(APPLICATION_JSON)
        ).andExpect(status().isOk)

        mockMvc.perform(
            post("/account/authenticate").content(fileAsString("/json/account/password-reset/POST-authenticate.json")).contentType(APPLICATION_JSON)
        ).andExpect(status().isUnauthorized)

        mockMvc.perform(
            post("/account/authenticate").content(fileAsString("/json/account/password-reset/POST-authenticate-new-password.json")).contentType(APPLICATION_JSON)
        ).andExpect(status().isOk)
    }

    @Test
    fun `Creating application should return expected application when retrieving`() {
        every { idService.applicationId() } returns UUID.fromString("4217e0ab-b881-44ca-97ab-743793cc0577")
        every { idService.clientId() } returns UUID.fromString("cb6643a6-e431-4a40-8106-ac674c9b7723")
        every { idService.clientSecret() } returns UUID.fromString("cb6fcd97-88cd-4184-be27-1999e60c626f")
        every { timeService.applicationCreated() } returns Instant.parse("2022-11-10T17:40:59.560966600Z")

        mockMvc.perform(
            post("/application").content(fileAsString("/json/application/create/POST-application.json")).contentType(APPLICATION_JSON)
        ).andExpect(status().isOk).andExpect(content().json(fileAsString("/json/application/create/POST-and-GET-application-response.json"), true))

        mockMvc.perform(
            get("/application/4217e0ab-b881-44ca-97ab-743793cc0577")
        ).andExpect(status().isOk).andExpect(content().json(fileAsString("/json/application/create/POST-and-GET-application-response.json"), true))
    }

    @Test
    fun `Deleting application should return expected application when retrieving`() {
        every { idService.applicationId() } returns UUID.fromString("b28729f5-b03e-4d9b-8d28-d9156b7e8d1a")
        every { idService.clientId() } returns UUID.fromString("d3cdde6f-7e69-450b-b5de-a6931f6cf964")
        every { idService.clientSecret() } returns UUID.fromString("6cef9dae-6a0a-49cb-aa8c-8c83af78a46e")
        every { timeService.applicationCreated() } returns Instant.parse("2022-11-10T18:40:59.560966600Z")

        mockMvc.perform(
            post("/application").content(fileAsString("/json/application/delete/POST-application.json")).contentType(APPLICATION_JSON)
        ).andExpect(status().isOk).andExpect(content().json(fileAsString("/json/application/delete/POST-and-GET-application-response.json"), true))

        mockMvc.perform(
            get("/application/b28729f5-b03e-4d9b-8d28-d9156b7e8d1a")
        ).andExpect(status().isOk).andExpect(content().json(fileAsString("/json/application/delete/POST-and-GET-application-response.json"), true))

        mockMvc.perform(
            delete("/application/b28729f5-b03e-4d9b-8d28-d9156b7e8d1a")
        ).andExpect(status().isNoContent)

        mockMvc.perform(
            get("/application/b28729f5-b03e-4d9b-8d28-d9156b7e8d1a")
        ).andExpect(status().isNotFound)

        mockMvc.perform(
            delete("/application/b28729f5-b03e-4d9b-8d28-d9156b7e8d1a")
        ).andExpect(status().isNotFound)
    }

    @Test
    fun `Authenticating account should return expected token`() {
        every { idService.accountId() } returns UUID.fromString("647ceb4c-00e5-4a34-aac8-aca5587ab6d3")
        every { idService.tokenId() } returns UUID.fromString("7bcc8fb7-a196-4eb5-9207-e9e991a3d167")
        every { timeService.accountCreated() } returns Instant.parse("2022-12-10T19:25:59.560966600Z")
        every { timeService.tokenIssuedAt() } returns Instant.parse("2022-12-10T19:26:59.560966600Z")

        mockMvc.perform(
            post("/account").content(fileAsString("/json/account/authenticate/POST-account.json")).contentType(APPLICATION_JSON)
        ).andExpect(status().isOk).andExpect(content().json(fileAsString("/json/account/authenticate/POST-and-GET-account-response.json"), true))

        mockMvc.perform(
            get("/account/647ceb4c-00e5-4a34-aac8-aca5587ab6d3")
        ).andExpect(status().isOk).andExpect(content().json(fileAsString("/json/account/authenticate/POST-and-GET-account-response.json"), true))

        val authenticationResponse = mockMvc.perform(
            post("/account/authenticate").content(fileAsString("/json/account/authenticate/POST-authenticate.json")).contentType(APPLICATION_JSON)
        ).andExpect(status().isOk).andReturn().response.contentAsString
        assertTokenResponse(authenticationResponse)
    }

    private fun assertTokenResponse(tokenResponse: String) {
        val resource = objectMapper.readValue<TokenResource>(tokenResponse)
        val (header, body) = resource.accessToken.split(".")
        JSONAssert.assertEquals(fileAsString("/json/account/authenticate/token-header.json"), String(Base64.getDecoder().decode(header)), true)
        JSONAssert.assertEquals(fileAsString("/json/account/authenticate/token-body.json"), String(Base64.getDecoder().decode(body)), true)
        assertThat(resource.expiresIn).isEqualTo(300)
    }
}
