package com.freedom.data.model


import com.freedom.utils.CHARACTER_TABLE
import org.jetbrains.exposed.sql.Table

object CharactersTable : Table(name = CHARACTER_TABLE){
    val id = integer(name = "id").autoIncrement()
    val name = varchar(name = "name", length = 50).uniqueIndex()
    val image = varchar(name="image", length = 200)
    val created = varchar(name="created", length = 100)

    override val primaryKey = PrimaryKey(id)
}