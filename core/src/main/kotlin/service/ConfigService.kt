package core.service

import core.db.repository.ConfigRepository
import core.grpc.*

class ConfigService(private val repository: ConfigRepository)
    : ConfigServiceGrpcKt.ConfigServiceCoroutineImplBase() {
    override suspend fun listConfigs(request: Empty): ConfigList {
        val configs = repository.getAll()
        val builder = ConfigList.newBuilder()
        configs.forEach {
            builder.addConfigs(
                Config.newBuilder()
                    .setId(it.id)
                    .setName(it.name)
                    .setJsonPath(it.jsonPath)
                    .build()
            )
        }
        return builder.build()
    }

    override suspend fun addConfig(request: ConfigInput): ConfigResponse {
        val id = repository.add(request.name, request.jsonPath)
        return ConfigResponse.newBuilder().setId(id).build()
    }

    override suspend fun deleteConfig(request: ConfigId): Empty {
        repository.remove(request.id)
        return Empty.getDefaultInstance()
    }
}
