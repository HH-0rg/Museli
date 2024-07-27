import java.io.File

fun getSongs(directoryPath: String): Array<String> {
    val directory = File(directoryPath)
    return if (directory.isDirectory) {
        directory.listFiles { file -> file.isFile && file.extension in listOf("mp3", "wav", "flac", "opus") }
            ?.map { it.name }
            ?.toTypedArray() ?: emptyArray()
    } else {
        emptyArray()
    }
}

fun getPlaylists(): Map<String, Array<String>> {
    return mapOf(
        "greetings" to arrayOf("Hello", "Hi", "Hey"),
        "farewells" to arrayOf("Goodbye", "Bye", "See you"),
        "languages" to arrayOf("Kotlin", "Java", "Swift")
    )
}