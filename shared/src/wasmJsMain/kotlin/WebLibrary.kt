import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class WebLibrary(private val rootUrl: String): CoroutineScope by CoroutineScope(Dispatchers.Default),
    Library() {
    override lateinit var songsList: List<String>
    override lateinit var playlists: Map<String, List<String>>
    val initialization: Job = launch {
        songsList = getSongsRemote(rootUrl).map { filePath ->
            val normalizedPath = filePath.replace("\\", "/")  // Normalize backslashes to forward slashes
            val fileName = normalizedPath.substringAfterLast("/")  // Extract the file name from the path
            "$rootUrl$SongEp/$fileName"  // Construct the URL
        }
        playlists = getPlaylistsRemote(rootUrl)
    }
}