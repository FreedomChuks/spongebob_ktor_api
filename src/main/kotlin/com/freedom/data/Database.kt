package com.freedom.data

import com.freedom.data.model.CharactersTable
import io.ktor.server.config.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.DatabaseConfig
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

/*
    Database Creation
 */
object DatabaseFactory {
    fun init(config: ApplicationConfig){
//        val user = config.property("ktor.database.user").getString()
//        val password = config.property("ktor.database.password").getString()
//        val url = config.property("ktor.database.url").getString()
//        val driver = config.property("ktor.database.driver").getString()

        val driverClass = config.property("storage.driverClassName").getString()
        val jdbcUrl = config.property("storage.jdbcURL").getString()

//        Database.connect(
//            user = user,
//            password = password,
//            url = url,
//            driver = driver
//        )

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