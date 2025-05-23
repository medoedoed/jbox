package core.db.repository

import core.db.dao.ConfigDao
import core.db.dto.ConfigDto

class ConfigRepository(private val dao: ConfigDao) {
    fun getAll(): List<ConfigDto> = dao.all()
    fun add(name: String, jsonPath: String): Int = dao.insert(name, jsonPath)
    fun remove(id: Int) = dao.delete(id)
}