package core.service

import config.CoreSettings
import core.database.dto.ConfigDto
import core.database.repository.ConnectionConfigRepository
import core.util.JsonSaver
import core.util.UriParser

class ConnectionConfigService(
    private val repository: ConnectionConfigRepository,
    private val settings: CoreSettings,
    private val uriParser: UriParser,
    private val jsonSaver: JsonSaver
) {
    fun listConfigs(): List<ConfigDto> {
        return repository.getAll()
    }

    fun addConfigFromUri(uri: String, name: String) {
        require(name.matches(Regex("^[a-zA-Z0-9_-]+$"))) { "Config name must be alphanumeric with - or _" }

    }

}