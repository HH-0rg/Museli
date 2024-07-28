// Thanks to https://github.com/SEAbdulbasit/MusicApp-KMP/

import kotlinx.browser.document
import org.w3c.dom.HTMLAudioElement

actual class MediaPlayerController actual constructor(val platformContext: PlatformContext) {
    private val audioElement = document.createElement("audio") as HTMLAudioElement

    actual fun setRoot(newRoot: String) {
        // TODO
    }
    actual suspend fun loadSongList(): List<String> {
        return getSongsRemote(platformContext.rootUrl)
    }
    actual fun prepare(
        song: String,
        listener: MediaPlayerListener
    ) {
        audioElement.src = song
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

    actual fun release() {
    }

}
