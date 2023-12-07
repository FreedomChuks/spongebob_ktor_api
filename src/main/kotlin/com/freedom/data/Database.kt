package com.freedom.data

import com.freedom.data.model.CharactersTable
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

/*
    Database Creation
 */
object DatabaseFactory {
    fun init(){
        val driverClass = "org.postgresql.Driver"
        val jdbcUrl ="jdbc:postgresql://localhost:5432/freedomchuks?user=freedomchuks&password=password"
        Database.connect(jdbcUrl,driverClass)
        transaction {
            SchemaUtils.create(CharactersTable)
        }
    }

    suspend fun <T> dbQuery(block: suspend ()->T):T =
        newSuspendedTransaction(Dispatchers.IO){
            block()
        }
}