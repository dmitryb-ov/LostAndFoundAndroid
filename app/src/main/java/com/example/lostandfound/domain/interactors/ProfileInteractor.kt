package com.example.lostandfound.domain.interactors

import com.example.lostandfound.data.model.User
import com.example.lostandfound.data.model.UserEntity

interface ProfileInteractor {

    suspend fun getUser(user: User): User

    suspend fun getUserByEmail(email: String): User

    suspend fun getUsers(): List<User>

    suspend fun addUser(user: User)

    suspend fun deleteUser(user: User)

    suspend fun updateUser(user: User)

    fun getCurrentUser(userEntity: UserEntity): UserEntity
}