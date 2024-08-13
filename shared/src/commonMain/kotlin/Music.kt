import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

val ListSongsEp = "/api/listsongs"
val ListPlaylistsEp = "/api/listplaylists"
val SongEp = "/api/song"

@Serializable
data class Songs(val songs: List<String>)

@Serializable
data class Playlists(val playlists: Map<String, List<String>>)

fun joinUrls(baseUrl: String, path: String): String {
    // Ensure there's exactly one slash between base URL and path
    val cleanBaseUrl = baseUrl.trimEnd('/')
    val cleanPath = path.trimStart('/')

    return "$cleanBaseUrl/$cleanPath"
}

suspend fun getSongsRemote(remote: String): List<String> {
    val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    return try {
        val req = joinUrls(remote, ListSongsEp)
        println(req)
        client.get(req).body<Songs>().songs
    } catch (e: Exception) {
        println("Error fetching songs: ${e.message}")
        emptyList()
    } finally {
        client.close()
    }
}

suspend fun getPlaylistsRemote(remote: String): Map<String, List<String>> {
    val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    return try {
        val req = joinUrls(remote, ListPlaylistsEp)
        println(req)
        client.get(req).body<Playlists>().playlists
    } catch (e: Exception) {
        println("Error fetching playlists: ${e.message}")
        emptyMap()
    } finally {
        client.close()
    }
}