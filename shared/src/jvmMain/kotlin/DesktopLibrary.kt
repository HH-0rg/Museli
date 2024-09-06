class DesktopLibrary(private val rootDir: String): Library {
    private val songsList: MutableList<String> = mutableListOf()
    private var currentSongIndex: Int? = null
    override suspend fun getPlaylists(): Map<String, List<String>> {
        return getPlaylists(rootDir)
    }

    override suspend fun getSongs(): List<String> {
        val tempSongsList = getSongs(rootDir)
        songsList.clear()
        songsList.addAll(tempSongsList)
        currentSongIndex = if (songsList.isNotEmpty()) 0 else null
        return songsList
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