import java.io.File

val SongExtensions = listOf("mp3", "wav", "flac", "opus", "m4a")

fun getSongs(rootDir: String): List<String> {
    val directory = File(rootDir)
    check(directory.isDirectory) { "$rootDir is not a directory" }
    return directory.listFiles { file -> file.isFile && file.extension in SongExtensions}
        ?.map { it.absolutePath } ?: emptyList()
}

fun getPlaylists(rootDir: String): Map<String, List<String>> {
    val directory = File(rootDir);
    check(directory.isDirectory) { "$rootDir is not a directory" }
    val playlists = mutableMapOf<String, MutableList<String>>()
    val directories = directory.listFiles { file -> file.isDirectory } ?: emptyArray()
    for (subDir in directories) {
        val musicFiles = mutableListOf<String>()

        val files = subDir.listFiles { file -> file.isFile && file.extension in SongExtensions }
            ?: emptyArray()

        for (file in files) {
            musicFiles.add(file.absolutePath)
        }

        playlists[subDir.name] = musicFiles
    }
    return playlists
}
