package config

import config.data.AppConfig
import config.data.defaultAppConfig
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.encodeToJsonElement
import java.nio.file.Files
import java.nio.file.Path

fun loadAppConfig(path: Path): AppConfig {
    val defaultConfig = defaultAppConfig()

    if (!Files.exists(path)) return defaultConfig

    val content = Files.readString(path)
    val json = Json {
        ignoreUnknownKeys = false
    }

    val userJsonElement = try {
        json.parseToJsonElement(content)
    } catch (e: SerializationException) {
        throw IllegalArgumentException("Invalid JSON format: ${e.message}", e)
    }

    if (userJsonElement !is JsonObject) {
        throw IllegalArgumentException("Config must be a JSON object")
    }

    val defaultJsonElement = Json.encodeToJsonElement(defaultConfig) as JsonObject
    val mergedJson = mergeJsonObjects(defaultJsonElement, userJsonElement)

    return try {
        json.decodeFromJsonElement(AppConfig.serializer(), mergedJson)
    } catch (e: SerializationException) {
        throw IllegalArgumentException("Failed to parse AppConfig: ${e.message}", e)
    }
}

fun saveAppConfig(path: Path, config: AppConfig) {
    Files.createDirectories(path.parent)
    Files.writeString(path, Json.encodeToString(AppConfig.serializer(), config))
}

private fun mergeJsonObjects(default: JsonObject, user: JsonObject): JsonObject {
    val merged = mutableMapOf<String, JsonElement>()

    for ((key, defaultValue) in default) {
        if (key in user) {
            val userValue = user[key]!!

            val newValue = when {
                defaultValue is JsonObject && userValue is JsonObject ->
                    mergeJsonObjects(defaultValue, userValue)

                else -> userValue
            }

            merged[key] = newValue
        } else {
            merged[key] = defaultValue
        }
    }

    for (key in user.keys) {
        if (key !in default.keys) {
            throw IllegalArgumentException("Unknown configuration field: '$key'")
        }
    }

    return JsonObject(merged)
}