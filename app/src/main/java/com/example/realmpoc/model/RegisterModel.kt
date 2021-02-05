package com.example.realmpoc.model

import io.realm.RealmObject

open class RegisterModel : RealmObject() {
    var name:String? = null
    var email:String? = null
    var password:String? = null
    var status:Int? = 0
}