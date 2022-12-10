package dev.jarand.oauthserver.utility

import java.util.stream.Collectors

fun fileAsString(file: String): String {
    val inputStream = {}::class.java.getResourceAsStream(file)
        ?: throw RuntimeException("Could not open input stream for file: $file")
    return inputStream.bufferedReader().lines().collect(Collectors.joining())
}
