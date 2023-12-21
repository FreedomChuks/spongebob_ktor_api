package com.freedom.utils

import com.cloudinary.Cloudinary
import io.ktor.server.config.*

/**
 * Init cloudinary
 */
object CloudinaryApp{
    fun configureCloudinary(config: ApplicationConfig)
            = Cloudinary(
        mapOf(
            "cloud_name" to config.property("ktor.cloudinary.cloud_name").getString(),
            "api_key" to config.property("ktor.cloudinary.api_key").getString(),
            "api_secret" to config.property("ktor.cloudinary.api_secret").getString()
        ),
    )

}
