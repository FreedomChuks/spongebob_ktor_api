package com.freedom.domain.usecase

import com.freedom.data.dao.CharacterDaoInterface
import com.freedom.domain.CharacterListResponse
import com.freedom.utils.ServiceResult

class GetAllCharacter(
    private val characterDao: CharacterDaoInterface
) {
    suspend operator fun invoke(): CharacterListResponse {
        return when(val response = characterDao.allCharacters()){
            is ServiceResult.Error -> {
                CharacterListResponse(listOf())
            }
            is ServiceResult.Success -> {
                CharacterListResponse(response.data)
            }
        }
    }
}