package com.freedom.domain

import com.freedom.data.model.Character
import kotlinx.serialization.Serializable

/*
   Character response model
 */
@Serializable
data class CharacterListResponse (
   val character: List<Character?>
)

@Serializable
data class CharacterResponse (
   val character: Character?
)