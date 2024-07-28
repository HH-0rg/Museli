// Thanks to https://github.com/SEAbdulbasit/MusicApp-KMP/

expect class MediaPlayerController(platformContext: PlatformContext) {
    suspend fun loadSongList(): List<String>
    fun setRoot(newRoot: String)
    fun prepare(song: String, listener: MediaPlayerListener)

    fun start()

    fun pause()

    fun stop()

    fun isPlaying(): Boolean

    fun release()
}

expect class PlatformContext