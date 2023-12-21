package com.freedom.domain.usecase

import com.freedom.data.dao.CharacterDaoInterface
import com.freedom.data.model.Characters
import com.freedom.domain.CharacterResponse
import com.freedom.domain.CloudinaryResponse
import com.freedom.utils.CloudinaryApp
import com.freedom.utils.ServiceResult
import com.freedom.utils.toDatabaseString
import io.ktor.server.config.*
import java.time.LocalDateTime

class AddCharacters(
    private val charactersDao: CharacterDaoInterface
) {
    suspend operator fun invoke(character: Characters, config:ApplicationConfig):CharacterResponse{
        return try {
            val response = CloudinaryApp.configureCloudinary(config)
                .uploader().upload(character.image,
                    mapOf(
                        "public_id" to "spongebob_res",
                        "resource_type" to "image"
                    )
                )
            val data = CloudinaryResponse(url = response["url"] as String)



            val dbResult = charactersDao.addCharacter(character.copy(
                created = LocalDateTime.now().toDatabaseString(),
                image = data.url
            ))
            when(dbResult){
                is ServiceResult.Error -> {
                    CharacterResponse(character)
                }
                is ServiceResult.Success -> {
                    CharacterResponse(dbResult.data)
                }
            }
        }catch (e:Exception){
            println("exception ${e}")
            CharacterResponse(character)
        }
    }
}