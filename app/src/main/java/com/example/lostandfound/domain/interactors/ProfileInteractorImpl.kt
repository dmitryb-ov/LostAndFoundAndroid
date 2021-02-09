package com.example.lostandfound.domain.interactors

import com.example.lostandfound.data.model.User
import com.example.lostandfound.data.model.UserEntity
import com.example.lostandfound.data.repository.ProfileRepository
import com.example.lostandfound.di.scope.ApplicationScope
import javax.inject.Inject

@ApplicationScope
class ProfileInteractorImpl @Inject constructor(
    val repository: ProfileRepository
) : ProfileInteractor {

    override suspend fun getUser(user: User): User {
        return repository.getUser(user)
    }

    override suspend fun getUserByEmail(email: String): User {
        return repository.getUserByEmail(email)
    }

    override suspend fun getUsers(): List<User> {
        return repository.getUsers()
    }

    override suspend fun addUser(user: User) {
        repository.addUser(user)
    }

    override suspend fun deleteUser(user: User) {
        repository.deleteUser(user)
    }

    override suspend fun updateUser(user: User) {
        repository.updateUser(user)
    }

    override fun getCurrentUser(userEntity: UserEntity): UserEntity {
        return repository.getCurrentUser(userEntity)
    }
}