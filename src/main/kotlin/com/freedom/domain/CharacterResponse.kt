package com.freedom.domain

import com.freedom.data.model.Characters
import kotlinx.serialization.Serializable

/*
   Character response model
 */
@Serializable
data class CharacterListResponse (
   val character: List<Characters?>
)

@Serializable
data class CharacterResponse (
   val character: Characters?
)

@Serializable
data class ErrorResponse(
   val code:Int,
   val message:String
)