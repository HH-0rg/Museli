import java.io.File
import java.io.FileInputStream
import java.io.InputStream

var SongExtensions = listOf("mp3", "wav", "flac", "opus", "m4a")

fun getSongs(rootDir: String?): List<String> {
    val safeRootDir = rootDir ?: return emptyList() // Handle null case if necessary

    val directory = File(safeRootDir)
    return if (directory.isDirectory) {
        directory.listFiles { file -> file.isFile && file.extension in SongExtensions}
            ?.map { it.name } ?: emptyList()
    } else {
        emptyList()
    }
}
