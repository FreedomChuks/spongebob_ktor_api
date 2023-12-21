package com.freedom.data.dao

import com.freedom.data.model.Characters
import com.freedom.utils.ServiceResult

interface CharacterDaoInterface {
    suspend fun allCharacters():ServiceResult<List<Characters>>
    suspend fun character(id:Int):ServiceResult<Characters?>
    suspend fun addCharacter(characters: Characters):ServiceResult<Characters?>
    suspend fun addCharacters(characters: List<Characters>)
}