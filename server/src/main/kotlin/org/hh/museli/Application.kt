package org.hh.museli

import Greeting
import SERVER_PORT
import getPlatform
import getSongs
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.serialization.kotlinx.json.*


fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json() // Use default JSON serialization
    }

    routing {
        get("/api/songs") {
            call.respond(getSongs("C:\\Users\\manas\\Music\\"))
        }
    }
}