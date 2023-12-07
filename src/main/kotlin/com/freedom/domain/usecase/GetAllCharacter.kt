package com.freedom.domain.usecase

import com.freedom.data.dao.CharacterDaoInterface
import com.freedom.domain.CharacterResponse
import com.freedom.utils.ServiceResult

class GetAllCharacter(
    private val characterDao: CharacterDaoInterface
) {
    suspend operator fun invoke():CharacterResponse{
        return when(val response = characterDao.allCharacters()){
            is ServiceResult.Error -> {
                CharacterResponse(listOf())
            }
            is ServiceResult.Success -> {
                CharacterResponse(response.data)
            }
        }
    }
}