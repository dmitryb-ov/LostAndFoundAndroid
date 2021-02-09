package com.example.lostandfound.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.lostandfound.data.model.FoundEntity

@Dao
interface FoundDao {
    @Insert
    fun insert(lost: FoundEntity)

    @Query("DELETE FROM found WHERE id = :foundId")
    fun delete(foundId: Long)

    @Query("SELECT * from found")
    fun getAllFound(): List<FoundEntity>

    @Query("SELECT * from found WHERE id = :foundId")
    fun getFound(foundId: Long): FoundEntity
}