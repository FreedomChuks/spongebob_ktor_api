package com.freedom

import com.freedom.data.model.Character
import com.freedom.domain.usecase.AddCharacters
import com.freedom.domain.usecase.GetAllCharacter
import com.freedom.domain.usecase.GetCharacter
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.characterRoute(
    addCharacters: AddCharacters,
    getAllCharacter: GetAllCharacter,
    getCharacter: GetCharacter

){
    post("/create") {
        /**
         [step 1] User Request
         [Step 2] insert data to database
         [Step 3] if success return request as response
        */
        val userRequest = call.receiveNullable<Character>()?:kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val response = addCharacters(userRequest)
        val httpsStatus = if (userRequest.name.isNotEmpty()) HttpStatusCode.Created else HttpStatusCode.BadGateway

        call.respond(
            status = httpsStatus,
            message = response

        )
    }

    get("/character") {
        val response  = getAllCharacter()
        call.respond(
            message = response
        )
    }

    get("/character/{id}") {
        val id = call.parameters["id"]?:run {
            call.respond(
                status = HttpStatusCode.BadRequest,
                message = "Invalid Parameter"
            )
            return@get
        }
        val response = getCharacter(id.toInt())
        val HttpStatus = if (response?.character?.name?.isNotEmpty() == true) HttpStatusCode.OK else HttpStatusCode.BadGateway
        call.respond(
            status = HttpStatus,
            message = response
        )

    }
}