package core.util

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File


class JsonSaver {
    fun saveConfig(config: Map<String, Any>, filePath: String): File {
        require(filePath.endsWith(".json")) { "Config filename must end with .json" }

        val targetFile = File(filePath)
        val json = Json { prettyPrint = true }
        val jsonString = json.encodeToString(config as Map<String, Any?>)
        targetFile.writeText(jsonString)

        return targetFile
    }
}