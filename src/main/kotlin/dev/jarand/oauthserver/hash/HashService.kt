package dev.jarand.oauthserver.hash

import org.springframework.stereotype.Service
import java.security.SecureRandom
import java.util.*
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec


@Service
class HashService {

    fun hash(plaintext: String): HashedText {
        val random = SecureRandom()
        val salt = ByteArray(16)
        random.nextBytes(salt)
        val keySpec = PBEKeySpec(plaintext.toCharArray(), salt, 65536, 128)
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        return HashedText(Base64.getEncoder().encodeToString(factory.generateSecret(keySpec).encoded), Base64.getEncoder().encodeToString(salt))
    }

    fun matches(plaintext: String, hashedText: HashedText): Boolean {
        val keySpec = PBEKeySpec(plaintext.toCharArray(), Base64.getDecoder().decode(hashedText.salt), 65536, 128)
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        val hash = Base64.getEncoder().encodeToString(factory.generateSecret(keySpec).encoded)
        return hash == hashedText.hash
    }
}
