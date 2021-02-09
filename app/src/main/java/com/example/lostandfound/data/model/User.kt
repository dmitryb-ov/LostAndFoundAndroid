package com.example.lostandfound.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "email")
    var email: String,
    @ColumnInfo(name = "password")
    val password: String = "",
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "city")
    var city: String? = "",
    @ColumnInfo(name = "phone_number")
    var phoneNumber: String? = "",
    @ColumnInfo(name = "registration_date")
    val registrationDate: String
) {
//    var password: String
//        get() = _password
//        set(value) {
//            _password = AESCrypt.encrypt(value)
//        }
}