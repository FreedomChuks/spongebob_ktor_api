package com.freedom.data.dao

import com.freedom.data.DatabaseFactory.dbQuery
import com.freedom.data.model.Characters
import com.freedom.data.model.CharactersTable
import com.freedom.utils.ErrorCode
import com.freedom.utils.ServiceResult
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.selectAll


class CharacterDaoImpl: CharacterDaoInterface {

    private fun rowToCharacter(row: ResultRow) = com.freedom.data.model.Characters(
        id = row[CharactersTable.id],
        name = row[CharactersTable.name],
        description = row[CharactersTable.description],
        color = row[CharactersTable.color],
        occupation = row[CharactersTable.occupation],
        residence = row[CharactersTable.residence],
        gender = row[CharactersTable.gender],
        image= row[CharactersTable.image],
        created = row[CharactersTable.created],
    )

    //Fetch All Character from DB
    override suspend fun allCharacters(): ServiceResult<List<Characters>> {
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

    //Fetch A single Character from DB
    override suspend fun character(id: Int): ServiceResult<Characters?> {
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

    //Insert A Character to DB
    override suspend fun addCharacter(characters: Characters): ServiceResult<Characters?> {
        return try {
            dbQuery {
                CharactersTable.insert {
                    it[name] = characters.name
                    it[description] = characters.description
                    it[gender] = characters.gender
                    it[occupation] = characters.occupation
                    it[residence] = characters.residence
                    it[color] = characters.color
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

    //Batch Insert Character to DB
    override suspend fun addCharacters(characters: List<Characters>) {
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