package dev.jarand.oauthserver

import dev.jarand.oauthserver.utility.ComponentTest
import dev.jarand.oauthserver.utility.fileAsString
import io.mockk.every
import org.junit.jupiter.api.Test
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
        every { timeService.accountCreated() } returns Instant.parse("2022-12-10T18:56:59.560966600Z")
        every { timeService.accountUpdated() } returns Instant.parse("2022-12-10T19:57:03.560966600Z")
        every { timeService.passwordResetCreated() } returns Instant.parse("2022-12-11T20:03:07.560966600Z")

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
}
