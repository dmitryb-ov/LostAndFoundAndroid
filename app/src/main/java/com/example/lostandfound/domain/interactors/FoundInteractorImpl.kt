package com.example.lostandfound.domain.interactors

import com.example.lostandfound.data.model.FoundEntity
import com.example.lostandfound.data.repository.FoundRepository
import com.example.lostandfound.di.scope.ApplicationScope
import javax.inject.Inject

@ApplicationScope
class FoundInteractorImpl @Inject constructor(
    val repository: FoundRepository
) : FoundInteractor {
    override suspend fun getFoundItems(): List<FoundEntity> {
        return repository.getFoundItems()
    }

    override suspend fun deleteFoundItem(found: FoundEntity) {
        repository.deleteLostItem(found)
    }

    override suspend fun addFoundItem(found: FoundEntity) {
        repository.addLostItem(found)
    }
}