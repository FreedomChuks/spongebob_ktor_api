package com.freedom.utils

enum class ErrorCode(val message: String) {
    API_KEY("There was a problem generating your API Key. Try again."),
    UNKNOWN("An unknown error has occurred."),
    CHARACTER_NOT_FOUND("Character not found"),
    DATABASE_ERROR("Unknown database error. Try again, and check your parameters."),
    INVALID_JSON("Your JSON must match the format in this sample response."),
    INVALID_TYPE("Hey! you must provide an id"),
    INVALID_API_KEY("Bad API key. Use x-api-key in the header.")
}
