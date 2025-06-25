package core.database.repository

import core.database.dao.ConnectionConfigDao
import core.database.dto.ConfigDto

class ConnectionConfigRepository(private val dao: ConnectionConfigDao) {
    fun getAll(): List<ConfigDto> = dao.all()
    fun add(name: String, jsonPath: String): Int = dao.insert(name, jsonPath)
    fun remove(id: Int) = dao.delete(id)
//    fun getById(id: Int) = dao.getById(id)
}


