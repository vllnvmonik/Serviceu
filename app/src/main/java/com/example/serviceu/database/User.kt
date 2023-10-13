package com.example.serviceu.database

data class User (
    var id: Int = -1,
    var fullname:String = "",
    var email: String = "",
    var phone: String = "",
    var address:String  = "",
    var role:String  = "",
    var category:String  = "",
    var password:String  = ""
)