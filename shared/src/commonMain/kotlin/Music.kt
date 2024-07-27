import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

val SongsEp = "/api/songs"
var testingMusicDir = "C:\\Users\\manas\\Music\\"
@Serializable
data class Songs(val songs: List<String>)

suspend fun getSongsRemote(remote: String): List<String> {
    val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    return try {
        client.get("${remote}${SongsEp}").body<Songs>().songs
    } catch (e: Exception) {
        println("Error fetching songs: ${e.message}")
        emptyList()
    } finally {
        client.close()
    }
}