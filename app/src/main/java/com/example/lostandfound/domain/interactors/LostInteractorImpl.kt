package com.example.lostandfound.domain.interactors

import com.example.lostandfound.data.model.LostEntity
import com.example.lostandfound.data.repository.LostRepository
import com.example.lostandfound.di.scope.ApplicationScope
import com.example.lostandfound.presentation.view.recyclerview.lost.LostModel
import javax.inject.Inject

@ApplicationScope
class LostInteractorImpl @Inject constructor(
    val repository: LostRepository
) : LostInteractor {
    override suspend fun getLostItems(): List<LostModel> {
        return repository.getLostItems()
    }

    override suspend fun deleteLostItem(lost: LostEntity) {
        repository.deleteLostItem(lost)
    }

    override suspend fun addLostItem(lost: LostEntity) {
        repository.addLostItem(lost)
    }


}