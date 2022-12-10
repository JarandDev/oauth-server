package dev.jarand.oauthserver.account.repository

import dev.jarand.oauthserver.account.domain.Account
import dev.jarand.oauthserver.account.domain.PasswordResetRequest
import dev.jarand.oauthserver.config.service.TimeService
import dev.jarand.oauthserver.hash.HashedText
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.time.Instant
import java.util.*

@Repository
class AccountRepository(private val jdbcTemplate: NamedParameterJdbcTemplate, private val timeService: TimeService) {

    fun save(account: Account) {
        jdbcTemplate.update(
            """
            INSERT INTO account (id, email, password_hash, password_salt, created, updated)
            VALUES (:id, :email, :password_hash, :password_salt, :created, :updated)
            """.trimIndent(),
            mapOf(
                "id" to account.id,
                "email" to account.email,
                "password_hash" to account.password.hash,
                "password_salt" to account.password.salt,
                "created" to account.created.toString(),
                "updated" to account.updated.toString()
            )
        )
    }

    fun updatePassword(password: HashedText, account: Account) {
        jdbcTemplate.update(
            """
            UPDATE account
            SET password_hash = :password_hash, password_salt = :password_salt, updated = :updated
            WHERE id = :id
            """.trimIndent(),
            mapOf(
                "password_hash" to password.hash,
                "password_salt" to password.salt,
                "id" to account.id,
                "updated" to timeService.accountUpdated().toString()
            )
        )
    }

    fun get(id: UUID): Account? {
        return try {
            jdbcTemplate.queryForObject(
                """
                SELECT id, email, password_hash, password_salt, created, updated
                FROM account
                WHERE id = :id
                """.trimIndent(),
                mapOf("id" to id)
            ) { resultSet, _ -> mapAccount(resultSet) }
        } catch (ex: EmptyResultDataAccessException) {
            null
        }
    }

    fun get(email: String): Account? {
        return try {
            jdbcTemplate.queryForObject(
                """
                SELECT id, email, password_hash, password_salt, created, updated
                FROM account
                WHERE email = :email
                """.trimIndent(),
                mapOf("email" to email)
            ) { resultSet, _ -> mapAccount(resultSet) }
        } catch (ex: EmptyResultDataAccessException) {
            null
        }
    }

    fun delete(id: UUID) {
        jdbcTemplate.update(
            """
            DELETE FROM account WHERE id = :id
            """.trimIndent(),
            mapOf("id" to id)
        )
    }

    fun save(passwordResetRequest: PasswordResetRequest) {
        jdbcTemplate.update(
            """
            INSERT INTO password_reset_request (id, email, token_hash, token_salt, created)
            VALUES (:id, :email, :token_hash, :token_salt, :created)
            """.trimIndent(),
            mapOf(
                "id" to passwordResetRequest.id,
                "email" to passwordResetRequest.email,
                "token_hash" to passwordResetRequest.token.hash,
                "token_salt" to passwordResetRequest.token.salt,
                "created" to passwordResetRequest.created.toString()
            )
        )
    }

    fun get(id: UUID, email: String): PasswordResetRequest? {
        return try {
            jdbcTemplate.queryForObject(
                """
                SELECT id, email, token_hash, token_salt, created
                FROM password_reset_request
                WHERE id = :id AND email = :email
                """.trimIndent(),
                mapOf(
                    "id" to id,
                    "email" to email
                )
            ) { resultSet, _ -> mapPasswordResetRequest(resultSet) }
        } catch (ex: EmptyResultDataAccessException) {
            null
        }
    }

    private fun mapAccount(resultSet: ResultSet): Account {
        return Account(
            resultSet.getObject("id", UUID::class.java),
            resultSet.getString("email"),
            HashedText(
                resultSet.getString("password_hash"),
                resultSet.getString("password_salt")
            ),
            Instant.parse(resultSet.getString("created")),
            Instant.parse(resultSet.getString("updated"))
        )
    }

    private fun mapPasswordResetRequest(resultSet: ResultSet): PasswordResetRequest {
        return PasswordResetRequest(
            resultSet.getObject("id", UUID::class.java),
            resultSet.getString("email"),
            "",
            HashedText(
                resultSet.getString("token_hash"),
                resultSet.getString("token_salt")
            ),
            Instant.parse(resultSet.getString("created"))
        )
    }
}
