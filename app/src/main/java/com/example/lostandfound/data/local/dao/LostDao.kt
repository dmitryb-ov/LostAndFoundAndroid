package com.example.lostandfound.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.lostandfound.data.model.LostEntity

@Dao
interface LostDao {
    @Insert
    fun insert(lost: LostEntity)

    @Query("DELETE FROM lost WHERE id = :lostId")
    fun delete(lostId: Long)

    @Query("SELECT * from lost")
    fun getAllLost(): List<LostEntity>

    @Query("SELECT * from lost WHERE id = :lostId")
    fun getLost(lostId: Long): LostEntity
}