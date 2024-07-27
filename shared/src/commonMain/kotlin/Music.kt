var platform: String = getPlatform().toString();
fun getSongs(): Array<String> {
    return arrayOf("Superman", "batman", "Shaktiman", "Hanuman")
}

fun getPlaylists(): Map<String, Array<String>> {
    return mapOf(
        "greetings" to arrayOf("Hello", "Hi", "Hey"),
        "farewells" to arrayOf("Goodbye", "Bye", "See you"),
        "languages" to arrayOf("Kotlin", "Java", "Swift")
    )
}
