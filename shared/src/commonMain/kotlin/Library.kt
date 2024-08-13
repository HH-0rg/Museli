abstract class Library {
    abstract var songsList: List<String>
    abstract var playlists: Map<String, List<String>>
    var currentSongIndex: Int = 0
    fun nextTrack() {
        currentSongIndex = (currentSongIndex + 1) % songsList.size
    }

    fun previousTrack() {
        currentSongIndex = (currentSongIndex - 1 + songsList.size) % songsList.size
    }

    fun getCurrentSong() = songsList[currentSongIndex]

    fun setCurrentSongIdx(idx: Int) {
        currentSongIndex = idx
    }
}
