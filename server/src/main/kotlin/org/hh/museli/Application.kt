package org.hh.museli

import SERVER_PORT
import Songs
import getSongs
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.serialization.kotlinx.json.*
import ListSongsEp
import SongEp
import SongRequest
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.withCharset
import io.ktor.server.request.receive
import kotlinx.serialization.json.Json
import testingMusicDir
import java.io.File

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json() // Use default JSON serialization
    }

    routing {
        get(ListSongsEp) {
            call.respondText(
                text = Json.encodeToString(Songs.serializer(), Songs(getSongs(testingMusicDir))),
                contentType = ContentType.Application.Json.withCharset(Charsets.UTF_8)
            )
        }
        post(SongEp) {
            val songRequest = call.receive<SongRequest>()
            val songFile = File(testingMusicDir, songRequest.song)

            if (songFile.exists() && songFile.isFile && songFile.parentFile.canonicalPath == File(testingMusicDir).canonicalPath) {
                call.respondFile(songFile)
            } else {
                call.respondText(
                    text = "Error: File not found or access denied.",
                    contentType = ContentType.Text.Plain,
                    status = HttpStatusCode.NotFound
                )
            }
        }
    }
}