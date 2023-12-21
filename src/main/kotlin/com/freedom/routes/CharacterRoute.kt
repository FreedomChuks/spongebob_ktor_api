package com.freedom.routes

import com.freedom.data.model.Characters
import com.freedom.domain.ErrorResponse
import com.freedom.domain.usecase.AddCharacters
import com.freedom.domain.usecase.GetAllCharacter
import com.freedom.domain.usecase.GetCharacter
import com.freedom.utils.ErrorCode
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
    post("/api/create") {
        /**
        [step 1] User Request
        [Step 2] insert data to database
        [Step 3] if success return request as response
         */

        val userRequest = call.receiveNullable<Characters>()?:kotlin.run {
            call.respond(
                HttpStatusCode.BadRequest,
                message = ErrorResponse(
                    code = HttpStatusCode.BadRequest.value,
                    message = ""
                )
            )
            return@post
        }
        val response = addCharacters(userRequest, application.environment.config)

        val httpsStatus = if (userRequest.name.isNotEmpty()) HttpStatusCode.Created else HttpStatusCode.BadGateway

        call.respond(
            status = httpsStatus,
            message = response
        )

    }

    get("/api/character") {
        val response  = getAllCharacter()
        call.respond(
            status = HttpStatusCode.OK,
            message = response
        )
    }

    get("/api/character/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()

        if (id ==null){
            call.respond(
                status = HttpStatusCode.BadRequest,
                message = ErrorResponse(
                    code = HttpStatusCode.BadRequest.value,
                    message = ErrorCode.INVALID_TYPE.message
                )
            )
            return@get
        }
        val response = getCharacter(id).character
        when {
            response?.id==null ->{
                call.respond(
                    HttpStatusCode.NotFound,
                    ErrorResponse(
                        code = HttpStatusCode.NotFound.value,
                        message = ErrorCode.CHARACTER_NOT_FOUND.message
                    )
                )
            }
            else->{
                call.respond(
                    status = HttpStatusCode.OK,
                    message = response
                )
            }
        }


    }


}