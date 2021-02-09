package com.example.lostandfound.domain.interactors

import com.example.lostandfound.data.model.LostEntity
import com.example.lostandfound.presentation.view.recyclerview.lost.LostModel

interface LostInteractor {

    suspend fun getLostItems(): List<LostModel>

    suspend fun deleteLostItem(lost: LostEntity)

    suspend fun addLostItem(lost: LostEntity)
}