package com.freedom.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Character(
    val id:Int = 0,
    val name:String = "",
    val image:String = "",
    val created:String =""
)
