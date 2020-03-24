package com.example.lostandfound.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "password")
    val password: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "second_name")
    val secondName: String? = "",
    @ColumnInfo(name = "city")
    val city: String? = "",
    @ColumnInfo(name = "phone_number")
    val phoneNumber: String? = "",
    @ColumnInfo(name = "registration_date")
    val registrationDate: String
) {
//    var password: String
//        get() = _password
//        set(value) {
//            _password = AESCrypt.encrypt(value)
//        }
}