package com.example.lostandfound.data.repository

import com.example.lostandfound.data.local.dao.FoundDao
import com.example.lostandfound.data.model.FoundEntity
import com.example.lostandfound.di.scope.ApplicationScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ApplicationScope
class FoundRepositoryImpl @Inject constructor(
    private var foundDao: FoundDao
) : FoundRepository {
    override suspend fun getFoundItems(): List<FoundEntity> = withContext(Dispatchers.IO) {
        val found = foundDao.getAllFound()
        found
    }

    override suspend fun getFoundItem(found: FoundEntity): FoundEntity =
        withContext(Dispatchers.IO) {
            val foundEntity = foundDao.getFound(found.id)
            foundEntity
        }

    override suspend fun deleteLostItem(found: FoundEntity) = withContext(Dispatchers.IO) {
        foundDao.delete(found.id)
    }

    override suspend fun addLostItem(found: FoundEntity) = withContext(Dispatchers.IO) {
        foundDao.insert(found)
    }

}