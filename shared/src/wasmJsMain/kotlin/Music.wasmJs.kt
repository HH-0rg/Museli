fun getSongs(): List<String> {
    return listOf("Superman", "batman", "Shaktiman", "Hanuman")
}

fun getPlaylists(): Map<String, List<String>> {
    return mapOf(
        "greetings" to listOf("Hello", "Hi", "Hey"),
        "farewells" to listOf("Goodbye", "Bye", "See you"),
        "languages" to listOf("Kotlin", "Java", "Swift")
    )
}
