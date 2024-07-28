package org.hh.museli

import ListPlaylistsEp
import SERVER_PORT
import Songs
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.serialization.kotlinx.json.*
import ListSongsEp
import Playlists
import SongEp
import getPlaylists
import getSongs
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.withCharset
import io.ktor.server.plugins.cors.routing.CORS
import kotlinx.serialization.json.Json
import java.io.File


var rootDir = ""
var frontendDomain = ""

fun main(args: Array<String>) {
    try {
        rootDir = args[0]
        frontendDomain = args[1]
    } catch (e: Exception) {
        println(e)
        println("CLI Args: <rootDir> <frontendDomain>")
    }
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json() // Use default JSON serialization
    }
    install(CORS) {
        anyHost()
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
    }

    routing {
        get(ListSongsEp) {
            call.respondText(
                text = Json.encodeToString(Songs.serializer(), Songs(getSongs(rootDir))),
                contentType = ContentType.Application.Json.withCharset(Charsets.UTF_8)
            )
        }
        get(ListPlaylistsEp) {
            call.respondText(
                text = Json.encodeToString(Playlists.serializer(), Playlists(getPlaylists(rootDir))),
                contentType = ContentType.Application.Json.withCharset(Charsets.UTF_8)
            )
        }
        // Route to serve a song file by filename
        get("$SongEp/{songName}") {
            val songName = call.parameters["songName"]
            if (songName != null) {
                val songFile = File(rootDir, songName)

                if (songFile.exists() && songFile.isFile && (songFile.parentFile.canonicalPath == File(rootDir).canonicalPath)) {
                    call.respondFile(songFile)
                } else {
                    call.respondText(
                        text = "Error: File not found or access denied.",
                        contentType = ContentType.Text.Plain,
                        status = HttpStatusCode.NotFound
                    )
                }
            } else {
                call.respondText(
                    text = "Error: Invalid request.",
                    contentType = ContentType.Text.Plain,
                    status = HttpStatusCode.BadRequest
                )
            }
        }
        // Route to serve a song file by filename
        get("$SongEp/{playlist}/{songName}") {
            val songName = call.parameters["songName"]
            val playlist = call.parameters["playlist"]
            if (songName != null) {
                val songFile = File(rootDir, "$playlist/$songName")

                if (songFile.exists() && songFile.isFile && (songFile.parentFile.parentFile.canonicalPath == File(rootDir).canonicalPath)) {
                    call.respondFile(songFile)
                } else {
                    call.respondText(
                        text = "Error: File not found or access denied.",
                        contentType = ContentType.Text.Plain,
                        status = HttpStatusCode.NotFound
                    )
                }
            } else {
                call.respondText(
                    text = "Error: Invalid request.",
                    contentType = ContentType.Text.Plain,
                    status = HttpStatusCode.BadRequest
                )
            }
        }
    }
}