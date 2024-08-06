// Thanks to https://github.com/SEAbdulbasit/MusicApp-KMP/
expect class MediaPlayerController(platformContext: PlatformContext) {
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

// TODO: remove PlatformContext
expect class PlatformContext