package com.freedom.domain.usecase

import com.freedom.data.dao.CharacterDaoInterface
import com.freedom.data.model.Character
import com.freedom.domain.CharacterResponse
import com.freedom.utils.ServiceResult
import com.freedom.utils.toDatabaseString
import java.time.LocalDateTime

class AddCharacters(
    private val charactersDao: CharacterDaoInterface
) {
    suspend operator fun invoke(character: Character):CharacterResponse{
        val dbResult = charactersDao.addCharacter(character.copy(
            created = LocalDateTime.now().toDatabaseString()
        ))
      return  when(dbResult){
            is ServiceResult.Error -> {
                CharacterResponse(listOf(character))
            }
            is ServiceResult.Success -> {
                CharacterResponse(listOf(dbResult.data))
            }
        }
    }
}