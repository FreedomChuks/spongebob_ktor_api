package com.freedom.data.dao

import com.freedom.data.model.Character
import com.freedom.utils.ServiceResult

interface CharacterDaoInterface {
    suspend fun allCharacters():ServiceResult<List<Character>>
    suspend fun character(id:Int):ServiceResult<Character?>
    suspend fun addCharacter(characters: Character):ServiceResult<Character?>
    suspend fun addCharacters(characters: List<Character>)
}