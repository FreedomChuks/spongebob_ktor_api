package com.freedom.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Characters(
    val id:Int = 0,
    val name:String = "",
    val gender:String = "",
    val occupation:String ="",
    val residence:String ="",
    val color:String ="",
    val description:String="",
    val image:String = "",
    val created:String =""
)