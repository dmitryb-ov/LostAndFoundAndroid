package com.example.lostandfound.data.repository

import android.util.Log
import com.example.lostandfound.data.local.dao.UserDao
import com.example.lostandfound.data.model.User
import com.example.lostandfound.data.model.UserEntity
import com.example.lostandfound.di.scope.ApplicationScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ApplicationScope
class ProfileRepositoryImpl @Inject constructor(
    private var profileDao: UserDao
) : ProfileRepository {

    override suspend fun getUsers(): List<User> = withContext(Dispatchers.IO) {
        val users = profileDao.getAll()
        users
    }

    override suspend fun getUserByEmail(email: String): User = withContext(Dispatchers.IO) {
        val user = profileDao.getUserByEmail(email)
//        Log.e("TAG", user.email)
        user
//        retuser
    }


    init {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val db = FirebaseDatabase.getInstance().reference.child("users")
        var user = UserEntity(currentUser?.email)
        db.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.e("TAG", "HELLO")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    user =
                        snapshot.child(currentUser?.uid.toString())
                            .getValue(UserEntity::class.java)!!
                    Log.e("TAG", "${user.name}, ${user.email}")
                    getCurrentUser(user)
                } catch (e: KotlinNullPointerException) {
                    Log.e("TAG", "${e.printStackTrace()}")
                }
            }
        })
    }

    override fun getCurrentUser(userEntity: UserEntity): UserEntity {
        Thread.sleep(500)
        return userEntity
    }

    override suspend fun getUser(user: User): User = withContext(Dispatchers.IO) {
        val userEntity = profileDao.getUserByEmail(user.email)
        userEntity
    }

    override suspend fun addUser(user: User) = withContext(Dispatchers.IO) {
        profileDao.addUser(user)
    }

    override suspend fun deleteUser(user: User) = withContext(Dispatchers.IO) {
        profileDao.deleteUser(user)
    }

    override suspend fun updateUser(user: User) = withContext(Dispatchers.IO) {
        profileDao.updateUser(user)
    }
}