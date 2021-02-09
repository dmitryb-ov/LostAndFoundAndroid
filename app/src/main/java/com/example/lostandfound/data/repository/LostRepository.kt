package com.example.lostandfound.data.repository

import com.example.lostandfound.data.model.LostEntity
import com.example.lostandfound.presentation.view.recyclerview.lost.LostModel

interface LostRepository {
    suspend fun getLostItems(): List<LostModel>

    suspend fun getLostItem(lost: LostEntity): LostEntity

    suspend fun deleteLostItem(lost: LostEntity)

    suspend fun addLostItem(lost: LostEntity)

//    suspend fun searchLosts(lost: List<LostEntity>): List<LostEntity>
}