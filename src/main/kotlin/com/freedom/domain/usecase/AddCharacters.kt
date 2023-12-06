package com.freedom.domain.usecase

import com.freedom.data.dao.CharacterDao
import com.freedom.data.model.Character
import com.freedom.domain.CharacterResponse
import com.freedom.utils.ServiceResult
import com.freedom.utils.toDatabaseString
import java.time.LocalDateTime

class AddCharacters(
    private val charactersDao: CharacterDao
) {
    suspend operator fun invoke(character: Character){
        val dbResult = charactersDao.addCharacter(character.copy(
            created = LocalDateTime.now().toDatabaseString()
        ))
        when(dbResult){
            is ServiceResult.Error -> {

            }
            is ServiceResult.Success -> {
                CharacterResponse(dbResult.data)
            }
        }
    }
}