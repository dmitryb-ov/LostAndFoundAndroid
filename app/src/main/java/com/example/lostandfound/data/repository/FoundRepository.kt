package com.example.lostandfound.data.repository

import com.example.lostandfound.data.model.FoundEntity

interface FoundRepository {
    suspend fun getFoundItems(): List<FoundEntity>

    suspend fun getFoundItem(found: FoundEntity): FoundEntity

    suspend fun deleteLostItem(found: FoundEntity)

    suspend fun addLostItem(found: FoundEntity)
}