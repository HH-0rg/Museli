import java.io.File

fun getSongs(directoryPath: String): List<String> {
    val directory = File(directoryPath)
    return if (directory.isDirectory) {
        directory.listFiles { file -> file.isFile && file.extension in listOf("mp3", "wav", "flac", "opus") }
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