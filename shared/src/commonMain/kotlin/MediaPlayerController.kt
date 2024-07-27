// Thanks to https://github.com/SEAbdulbasit/MusicApp-KMP/

expect class MediaPlayerController(platformContext: PlatformContext) {
    fun prepare(pathSource: String, listener: MediaPlayerListener)

    fun start()

    fun pause()

    fun stop()

    fun isPlaying(): Boolean

    fun release()
}

expect class PlatformContext