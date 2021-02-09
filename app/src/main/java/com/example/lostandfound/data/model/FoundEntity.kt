package com.example.lostandfound.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "found")
class FoundEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo(name = "user_name")
    val userName: String,
    @ColumnInfo(name = "author_id")
    val userId: Long,
    @ColumnInfo(name = "lost_head")
    val lostHead: String,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "location")
    val location: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "phone_number")
    val phoneNumber: String,
    @ColumnInfo(name = "found_date")
    val lostDate: String
)