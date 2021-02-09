package com.example.lostandfound.presentation.view.recyclerview.lost

data class LostModel(
    var userName: String = ""
) {
    var lostPic: String = ""
    var lostHead: String = ""
    var date: String = ""
    var location: String = ""
    var description: String = ""
    var phoneNumber: String = ""
    var lostDate: String = ""
    var authorId: String = ""

    constructor(
        userName: String,
        lostHead: String,
        lostDescription: String,
        phoneNumber: String,
        date: String,
        lostPic: String,
        pubDate: String,
        authorId: String
    ) : this(userName) {
        this.userName = userName
        this.lostHead = lostHead
        this.date = date
        this.description = lostDescription
        this.phoneNumber = phoneNumber
        this.lostPic = lostPic
        this.lostDate = pubDate
        this.authorId = authorId
    }

//    constructor(
//        userName: String,
//        lostPic: String,
//        lostHead: String,
//        date: String,
//        description: String,
//        location: String,
//        phoneNumber: String,
//        lostDate: String
//    ) : this(userName) {
//        this.userName = userName
//        this.lostHead = lostHead
//        this.lostPic = lostPic
//        this.date = date
//        this.description = description
//        this.location = location
//        this.phoneNumber = phoneNumber
//        this.lostDate = lostDate
//    }
}