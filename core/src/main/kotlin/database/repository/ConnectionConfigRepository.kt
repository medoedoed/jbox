package core.database.repository

import core.database.dao.ConfigDao
import core.database.dto.ConfigDto

class ConnectionConfigRepository(private val dao: ConfigDao) {
    fun getAll(): List<ConfigDto> = dao.all()
    fun add(name: String, jsonPath: String): Int = dao.insert(name, jsonPath)
    fun remove(id: Int) = dao.delete(id)
//    fun getById(id: Int) = dao.getById(id)
}


