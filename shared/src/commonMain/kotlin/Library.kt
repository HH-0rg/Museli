interface Library {
    suspend fun getPlaylists(): Map<String, List<String>>
    suspend fun getSongs(): List<String>

    fun nextTrack()

    fun previousTrack()

    fun getCurrentSong(): String?

    fun setCurrentSongIdx(idx: Int)
}
