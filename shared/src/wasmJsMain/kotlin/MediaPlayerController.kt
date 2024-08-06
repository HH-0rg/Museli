// Thanks to https://github.com/SEAbdulbasit/MusicApp-KMP/

import kotlinx.browser.document
import org.w3c.dom.HTMLAudioElement

actual class MediaPlayerController actual constructor(val platformContext: PlatformContext) {
    private val audioElement = document.createElement("audio") as HTMLAudioElement
    actual fun prepare(
        songUri: String,
        listener: MediaPlayerListener
    ) {
        println("listening to")
        println(songUri)
        audioElement.src = songUri
        audioElement.addEventListener("canplaythrough", {
            // Audio is ready to play without interruption
            listener.onReady()
            audioElement.play()
        })

        audioElement.onended = {
            listener.onAudioCompleted()
        }
        audioElement.addEventListener("error", {
            listener.onError()
        })

    }

    actual fun start() {
        audioElement.play()
    }

    actual fun pause() {
        audioElement.pause()
    }

    actual fun stop() {
    }

    actual fun isPlaying(): Boolean {
        return !audioElement.paused
    }

    actual fun seek(): Long? {
        return audioElement.currentTime.toLong()
    }

    actual fun mediaDuration(): Long? {
        return audioElement.duration.toLong()
    }

    actual fun setTime(time: Long) {
//        mediaPlayer?.controls()?.setTime(time)
        audioElement.currentTime = time.toDouble()
    }

    actual fun release() {
    }
}
