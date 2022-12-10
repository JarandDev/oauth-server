package dev.jarand.oauthserver.application.repository

import dev.jarand.oauthserver.application.domain.Application
import dev.jarand.oauthserver.hash.HashedText
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.time.Instant
import java.util.*

@Repository
class ApplicationRepository(private val jdbcTemplate: NamedParameterJdbcTemplate) {

    fun save(application: Application) {
        jdbcTemplate.update(
            """
            INSERT INTO application (id, name, description, home_page_url, privacy_policy_url, client_id, client_secret_hash, client_secret_salt, owner_account_id, created, updated)
            VALUES (:id, :name, :description, :home_page_url, :privacy_policy_url, :client_id, :client_secret_hash, :client_secret_salt, :owner_account_id, :created, :updated)
            """.trimIndent(),
            mapOf(
                "id" to application.id,
                "name" to application.name,
                "description" to application.description,
                "home_page_url" to application.homePageUrl,
                "privacy_policy_url" to application.privacyPolicyUrl,
                "client_id" to application.clientId,
                "client_secret_hash" to application.clientSecret.hash,
                "client_secret_salt" to application.clientSecret.salt,
                "owner_account_id" to application.ownerAccountId,
                "created" to application.created.toString(),
                "updated" to application.updated.toString()
            )
        )
    }

    fun get(id: UUID): Application? {
        return try {
            jdbcTemplate.queryForObject(
                """
                SELECT id, name, description, home_page_url, privacy_policy_url, client_id, client_secret_hash, client_secret_salt, owner_account_id, created, updated
                FROM application
                WHERE id = :id
                """.trimIndent(),
                mapOf("id" to id)
            ) { resultSet, _ -> mapRow(resultSet) }
        } catch (ex: EmptyResultDataAccessException) {
            null
        }
    }

    fun delete(id: UUID) {
        jdbcTemplate.update(
            """
            DELETE FROM application WHERE id = :id
            """.trimIndent(),
            mapOf("id" to id)
        )
    }

    private fun mapRow(resultSet: ResultSet): Application {
        return Application(
            resultSet.getObject("id", UUID::class.java),
            resultSet.getString("name"),
            resultSet.getString("description"),
            resultSet.getString("home_page_url"),
            resultSet.getString("privacy_policy_url"),
            resultSet.getString("client_id"),
            HashedText(
                resultSet.getString("client_secret_hash"),
                resultSet.getString("client_secret_salt")
            ),
            resultSet.getObject("owner_account_id", UUID::class.java),
            Instant.parse(resultSet.getString("created")),
            Instant.parse(resultSet.getString("updated"))
        )
    }
}
