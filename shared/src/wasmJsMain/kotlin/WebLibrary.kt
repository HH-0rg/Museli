import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class WebLibrary(private val rootUrl: String): CoroutineScope by CoroutineScope(Dispatchers.Default),
    Library() {
    override lateinit var songsList: List<String>
    override lateinit var playlists: Map<String, List<String>>
    // https://www.reddit.com/r/Kotlin/comments/lx99rv/comment/gpmmsr9/?utm_source=share&utm_medium=web3x&utm_name=web3xcss&utm_term=1&utm_content=share_button
    val initialization: Job = launch {
        songsList = getSongsRemote(rootUrl).map { filePath ->
            val normalizedPath = filePath.replace("\\", "/")  // Normalize backslashes to forward slashes
            val fileName = normalizedPath.substringAfterLast("/")  // Extract the file name from the path
            "$rootUrl$SongEp/$fileName"  // Construct the URL
        }
        playlists = getPlaylistsRemote(rootUrl)
        checkSongsList()
    }
}