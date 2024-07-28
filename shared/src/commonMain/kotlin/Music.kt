import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsChannel
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

val ListSongsEp = "/api/listsongs"
val SongEp = "/api/song"

@Serializable
data class Songs(val songs: List<String>)

@Serializable
data class SongRequest(val song: String)

suspend fun getSongsRemote(remote: String): List<String> {
    val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    return try {
        client.get("${remote}${ListSongsEp}").body<Songs>().songs
    } catch (e: Exception) {
        println("Error fetching songs: ${e.message}")
        emptyList()
    } finally {
        client.close()
    }
}