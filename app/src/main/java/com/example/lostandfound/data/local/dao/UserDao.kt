package com.example.lostandfound.data.local.dao

import androidx.room.*
import com.example.lostandfound.data.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM app_user")
    fun getAll(): List<User>

    @Query("SELECT * FROM app_user WHERE email == :email")
    fun getUserByEmail(email: String): User

    @Query("SELECT * FROM app_user WHERE id == :id")
    fun getUserById(id: Int): User

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun addUser(user: User)

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)
}