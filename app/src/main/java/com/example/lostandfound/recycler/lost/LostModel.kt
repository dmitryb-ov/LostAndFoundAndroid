package com.example.lostandfound.recycler.lost

class LostModel {
    var userName: String = ""
    var lostPic: Int = 0
    var lostHead: String = ""
    var date: String = ""
    var location: String = ""
    var description: String = ""
    var phoneNumber: String = ""
    var lostDate: String = ""

    constructor(
        userName: String,
        lostPic: Int,
        lostHead: String,
        date: String
    ) {
        this.userName = userName
        this.lostHead = lostHead
        this.lostPic = lostPic
        this.date = date
    }

    constructor(
        userName: String,
        lostPic: Int,
        lostHead: String,
        date: String,
        description: String,
        location: String,
        phoneNumber: String,
        lostDate: String
    ) {
        this.userName = userName
        this.lostHead = lostHead
        this.lostPic = lostPic
        this.date = date
        this.description = description
        this.location = location
        this.phoneNumber = phoneNumber
        this.lostDate = lostDate
    }
}