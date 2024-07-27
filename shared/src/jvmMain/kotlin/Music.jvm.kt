import java.io.File
import java.io.FileInputStream
import java.io.InputStream

var SongExtensions = listOf("mp3", "wav", "flac", "opus", "m4a")

fun getSongs(directoryPath: String): List<String> {
    val directory = File(directoryPath)
    return if (directory.isDirectory) {
        directory.listFiles { file -> file.isFile && file.extension in SongExtensions}
            ?.map { it.name } ?: emptyList()
    } else {
        emptyList()
    }
}
fun getPlaylists(): Map<String, List<String>> {
    return mapOf(
        "greetings" to listOf("Hello", "Hi", "Hey"),
        "farewells" to listOf("Goodbye", "Bye", "See you"),
        "languages" to listOf("Kotlin", "Java", "Swift")
    )
}