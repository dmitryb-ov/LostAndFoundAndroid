package com.example.lostandfound.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lostandfound.data.local.dao.FoundDao
import com.example.lostandfound.data.local.dao.LostDao
import com.example.lostandfound.data.local.dao.UserDao
import com.example.lostandfound.data.model.FoundEntity
import com.example.lostandfound.data.model.LostEntity
import com.example.lostandfound.data.model.User

@Database(entities = [User::class, LostEntity::class, FoundEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun lostDao(): LostDao
    abstract fun foundDao(): FoundDao
}