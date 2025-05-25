package core.service

import config.CoreSettings
import core.database.repository.ConnectionConfigRepository
import core.grpc.*
import core.util.JsonSaver
import core.util.UriParser
import java.io.File
import kotlin.io.path.Path
import kotlin.io.path.absolutePathString

class ConnectionConfigService(
    private val repository: ConnectionConfigRepository,
    private val settings: CoreSettings,
    private val uriParser: UriParser,
    private val jsonSaver: JsonSaver
) : ConnectionConfigServiceGrpcKt.ConnectionConfigServiceCoroutineImplBase() {

    override suspend fun listConfigs(request: Empty): ConfigList {
        val configs = repository.getAll()
        return ConfigList.newBuilder().apply {
            configs.forEach {
                addConfigs(
                    Config.newBuilder()
                        .setId(it.id)
                        .setName(it.name)
                        .setJsonPath(it.jsonPath)
                        .build()
                )
            }
        }.build()
    }

    override suspend fun addConfigFromUri(request: ConfigUriInput): ConfigResponse {
        val uri = request.uri
        val name = request.name

        require(name.matches(Regex("^[a-zA-Z0-9_-]+$"))) { "Config name must be alphanumeric with - or _" }

        val parsedJson = uriParser.parseUri(uri)
        val filePath = settings.app.jsonDir + File.separator + name + ".json"
        jsonSaver.saveConfig(parsedJson, filePath)
        val id = repository.add(name, Path(filePath).absolutePathString())

        return ConfigResponse.newBuilder().setId(id).build()
    }

//    override suspend fun addConfigFromJson(request: ConfigJsonInput): ConfigResponse {
//        val jsonPath = request.jsonPath
//        val name = request.name
//
//        val sourceFile = File(jsonPath)
//        require(sourceFile.exists()) { "File at path $jsonPath does not exist" }
//
//        val configFile = CoreSettings.copyConfig(sourceFile, name)
//        val id = repository.add(name, configFile.absolutePath)
//
//        return ConfigResponse.newBuilder().setId(id).build()
//    }
//
//    override suspend fun getActiveConfig(request: Empty): ActiveConfigResponse {
//        val active = settings
//            ?: return ActiveConfigResponse.getDefaultInstance()
//
//        return ActiveConfigResponse.newBuilder()
//            .setId(active.id)
//            .setName(active.name)
//            .setJsonPath(active.jsonPath)
//            .build()
//    }
//
//    override suspend fun setActiveConfig(request: ConfigId): Empty {
//        val config = repository.getById(request.id)
//            ?: error("Config with id ${request.id} not found")
//
//        CoreSettings.setActive(config)
//        return Empty.getDefaultInstance()
//    }
//
//    override suspend fun renameConfig(request: RenameConfigRequest): Empty {
//        val config = repository.getById(request.id)
//            ?: error("Config with id ${request.id} not found")
//
//        repository.rename(config.id, request.newName)
//        return Empty.getDefaultInstance()
//    }
//
//    override suspend fun deleteConfig(request: ConfigId): Empty {
//        repository.remove(request.id)
//        return Empty.getDefaultInstance()
//    }
}