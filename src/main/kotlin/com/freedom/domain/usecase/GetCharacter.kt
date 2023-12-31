package com.freedom.domain.usecase

import com.freedom.data.dao.CharacterDaoInterface
import com.freedom.data.model.Characters
import com.freedom.domain.CharacterResponse
import com.freedom.utils.ServiceResult

class GetCharacter(
   private val character:CharacterDaoInterface
) {
    suspend operator fun invoke(id:Int) : CharacterResponse {
        return when(val response = character.character(id)){
            is ServiceResult.Error -> {
                CharacterResponse(Characters())
            }
            is ServiceResult.Success -> {
                CharacterResponse(response.data)
            }
        }
    }
}