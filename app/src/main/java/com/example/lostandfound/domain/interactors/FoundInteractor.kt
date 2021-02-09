package com.example.lostandfound.domain.interactors

import com.example.lostandfound.data.model.FoundEntity

interface FoundInteractor {
    suspend fun getFoundItems(): List<FoundEntity>

    suspend fun deleteFoundItem(found: FoundEntity)

    suspend fun addFoundItem(found: FoundEntity)
}