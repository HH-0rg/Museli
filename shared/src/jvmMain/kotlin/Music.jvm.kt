import java.io.File

var SongExtensions = listOf("mp3", "wav", "flac", "opus", "m4a")

fun getSongs(rootDir: String): List<String> {
    val safeRootDir = rootDir ?: return emptyList() // Handle null case if necessary

    val directory = File(safeRootDir)
    return if (directory.isDirectory) {
        directory.listFiles { file -> file.isFile && file.extension in SongExtensions}
            ?.map { it.absolutePath } ?: emptyList()
    } else {
        emptyList()
    }
}

fun getPlaylists(rootDir: String): Map<String, List<String>> {
    val rootDirFile = File(rootDir);
    val result = mutableMapOf<String, MutableList<String>>()

    val directories = rootDirFile.listFiles { file -> file.isDirectory } ?: emptyArray()

    for (dir in directories) {
        val musicFiles = mutableListOf<String>()

        val files = dir.listFiles { file -> file.isFile && file.extension in SongExtensions }
            ?: emptyArray()

        for (file in files) {
            musicFiles.add(file.absolutePath)
        }

        result[dir.name] = musicFiles
    }
    return result
}
