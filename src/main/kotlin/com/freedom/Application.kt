package com.freedom

import com.freedom.data.DatabaseFactory
import com.freedom.plugins.configureKoin
import com.freedom.plugins.configureRouting
import com.freedom.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    DatabaseFactory.init(environment.config)
    configureSerialization()
    configureKoin()
    configureRouting()
}
