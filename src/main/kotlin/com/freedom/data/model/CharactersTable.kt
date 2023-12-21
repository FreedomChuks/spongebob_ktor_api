package com.freedom.data.model


import com.freedom.utils.CHARACTER_TABLE
import org.jetbrains.exposed.sql.Table

object CharactersTable : Table(name = CHARACTER_TABLE){
    val id = integer(name = "id").autoIncrement()
    val name = varchar(name = "name", length = 50).uniqueIndex()
    val image = varchar(name="image", length = 200)
    val gender = varchar(name = "gender", length = 10)
    val occupation = varchar(name = "occupation" , length = 25)
    val residence = varchar(name= "residence", length = 80)
    val color = varchar(name = "color", length = 20)
    val description = varchar(name = "description", length = 100)
    val created = varchar(name="created", length = 100)

    override val primaryKey = PrimaryKey(id)
}
