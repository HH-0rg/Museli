interface MediaPlayerController {
    fun prepare(songUri: String, listener: MediaPlayerListener)

    fun start()

    fun pause()

    fun stop()

    fun isPlaying(): Boolean

    fun release()

    fun seek(): Long?

    fun mediaDuration(): Long?

    fun setTime(time: Long)
}
