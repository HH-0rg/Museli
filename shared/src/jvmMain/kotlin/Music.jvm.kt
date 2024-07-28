import java.io.File
import java.io.FileInputStream
import java.io.InputStream

var SongExtensions = listOf("mp3", "wav", "flac", "opus", "m4a")

fun getSongs(rootDir: String?): List<String> {
    val safeRootDir = rootDir ?: return emptyList() // Handle null case if necessary
    println(rootDir)

    val directory = File(safeRootDir)
    println(directory)
    return if (directory.isDirectory) {
        println(directory.listFiles())
        directory.listFiles { file -> file.isFile && file.extension in SongExtensions}
            ?.map { it.name } ?: emptyList()
    } else {
        emptyList()
    }
}

fun getPlaylists(rootDir: String?): Map<String, List<String>> {
    val rootDir_ = rootDir?.let { File(it) } ?: return emptyMap()
    val result = mutableMapOf<String, MutableList<String>>()

    val directories = rootDir_.listFiles { file -> file.isDirectory } ?: emptyArray()

    for (dir in directories) {
        val musicFiles = mutableListOf<String>()

        val files = dir.listFiles { file -> file.isFile && file.extension in SongExtensions }
            ?: emptyArray()

        for (file in files) {
            musicFiles.add(file.name)
        }

        result[dir.name] = musicFiles
    }
    return result
}
