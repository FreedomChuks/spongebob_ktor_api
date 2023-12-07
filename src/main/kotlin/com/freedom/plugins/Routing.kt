package com.freedom.plugins

import com.freedom.characterRoute
import com.freedom.domain.usecase.AddCharacters
import com.freedom.domain.usecase.GetAllCharacter
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting(
) {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        post("*") {
            call.respond(
                status = HttpStatusCode.BadRequest,
                message = "Invalid route "
            )

        }

        val addCharacters by  inject<AddCharacters>()
        val getCharacters by  inject<GetAllCharacter>()
        characterRoute(addCharacters,getCharacters)
    }

}
