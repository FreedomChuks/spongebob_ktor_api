package com.freedom.data.dao

import com.freedom.data.DatabaseFactory.dbQuery
import com.freedom.data.model.Character
import com.freedom.data.model.CharactersTable
import com.freedom.domain.CharacterResponse
import com.freedom.utils.ErrorCode
import com.freedom.utils.ServiceResult
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.selectAll


class CharacterDaoImpl: CharacterDao {

    private fun rowToCharacter(row: ResultRow) = Character(
        id = row[CharactersTable.id],
        name = row[CharactersTable.name],
        image= row[CharactersTable.image],
        created = row[CharactersTable.created]
    )

    override suspend fun allCharacters(): ServiceResult<List<Character>> {
        return try {
            dbQuery {
                val response = CharactersTable.selectAll().map(::rowToCharacter)
                ServiceResult.Success(response)
            }
        }catch (e:Exception){
            when(e){
                is ExposedSQLException->{
                    println("exception from select function: ${e.errorCode}")
                    ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                }
            }
            ServiceResult.Error(ErrorCode.DATABASE_ERROR)
        }
    }

    override suspend fun character(id: Int): ServiceResult<Character?> {
        return try {
            dbQuery {
                val response = CharactersTable.select {
                    (CharactersTable.id eq id)
                }.map(::rowToCharacter).singleOrNull()
                ServiceResult.Success(response)
            }
        }catch (e:Exception){
            when(e){
                is ExposedSQLException->{
                    ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                }
            }
            ServiceResult.Error(ErrorCode.DATABASE_ERROR)
        }
    }

    override suspend fun addCharacter(characters: Character) {
        try {
            dbQuery {
                CharactersTable.insert {
                    it[id] = characters.id
                    it[name] = characters.name
                    it[image] = characters.image
                    it[created] = characters.created
                }.resultedValues?.singleOrNull()?.let {
                    ServiceResult.Success(rowToCharacter(it))
                }?: ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }catch (e:Exception){
            when (e){
                is ExposedSQLException->{
                    //catch constraint error
                    ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                }
                else->{
                    ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                }
            }
        }
    }

    override suspend fun addCharacters(characters: List<Character>) {
        try {
            dbQuery {
                CharactersTable.batchInsert(characters){ characterResponse ->
                    this[CharactersTable.id] = characterResponse.id
                    this[CharactersTable.name] = characterResponse.name
                    this[CharactersTable.image] = characterResponse.image
                    this[CharactersTable.created] = characterResponse.created
                }.singleOrNull()?.let {
                    ServiceResult.Success(rowToCharacter(it))
                }?: ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
        }catch (e:Exception){
            when (e){
                is ExposedSQLException->{
                    //catch constraint error
                    ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                }
                else->{
                    ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                }
            }
        }
    }
}