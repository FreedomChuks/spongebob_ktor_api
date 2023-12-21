package com.freedom.plugins

import com.freedom.domain.ErrorResponse
import com.freedom.routes.characterRoute
import com.freedom.domain.usecase.AddCharacters
import com.freedom.domain.usecase.GetAllCharacter
import com.freedom.domain.usecase.GetCharacter
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
                message = ErrorResponse(
                    code = HttpStatusCode.NotFound.value,
                    message = "There is nothing here."
                )
            )

        }

        val addCharacters by  inject<AddCharacters>()
        val getCharacters by  inject<GetAllCharacter>()
        val getCharacter by  inject<GetCharacter>()
        characterRoute(addCharacters,getCharacters,getCharacter)
    }

}
