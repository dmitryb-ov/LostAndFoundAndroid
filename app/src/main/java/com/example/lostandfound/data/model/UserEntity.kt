package com.example.lostandfound.data.model

data class UserEntity(
    var email: String? = ""
) {

    var name: String = "Пусто"
    var phone: String = ""
    var city: String = ""
    var userId: String? = "0"

    constructor(
        userId: String?,
        email: String?,
        name: String,
        phone: String,
        city: String
    ) : this(email) {
        this.userId = userId
        this.email = email
        this.name = name
        this.city = city
        this.phone = phone
    }
}