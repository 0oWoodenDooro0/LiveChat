package com.example.livechat

data class Item(var name:String = "",
                var imageurl:String = "")

var user = User()
class User{
    var uid:String = ""
    var email:String = ""
    var name:String = ""
    var sex:String = ""
    var birthday:String = ""
    var imageurl:String = ""
}
