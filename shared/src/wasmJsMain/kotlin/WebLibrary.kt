class WebLibrary(private val rootUrl: String): Library {
    private val songsList: MutableList<String> = mutableListOf()
    private var currentSongIndex: Int? = null
    override suspend fun getSongs(): List<String> {
        var tempSongsList = getSongsRemote(rootUrl)
        val songsListWithUrls = tempSongsList.map { filePath ->
            val normalizedPath = filePath.replace("\\", "/")  // Normalize backslashes to forward slashes
            val fileName = normalizedPath.substringAfterLast("/")  // Extract the file name from the path
            "$rootUrl$SongEp/$fileName"  // Construct the URL
        }
        songsList.clear()
        songsList.addAll(songsListWithUrls)
        currentSongIndex = if (songsList.isNotEmpty()) 0 else null
        return songsList

    }

    override suspend fun getPlaylists(): Map<String, List<String>> {
        return getPlaylistsRemote(rootUrl)
    }

    override fun nextTrack() {
        if (songsList.isNotEmpty() && currentSongIndex != null) {
            currentSongIndex = (currentSongIndex!! + 1) % songsList.size
        }
    }

    override fun previousTrack() {
        if (songsList.isNotEmpty() && currentSongIndex != null) {
            currentSongIndex = (currentSongIndex!! - 1 + songsList.size) % songsList.size
        }
    }

    override fun getCurrentSong(): String? {
        return currentSongIndex?.let { songsList[it] }
    }

    override fun setCurrentSongIdx(idx: Int) {
        currentSongIndex = idx
    }
}