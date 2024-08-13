import kotlinx.browser.document
import org.w3c.dom.HTMLAudioElement

class WebController: MediaPlayerController {
    private val audioElement = document.createElement("audio") as HTMLAudioElement
    override fun prepare(
        songUri: String,
        listener: MediaPlayerListener
    ) {
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

    override fun start() {
        audioElement.play()
    }

    override fun pause() {
        audioElement.pause()
    }

    override fun stop() {
    }

    override fun isPlaying(): Boolean {
        return !audioElement.paused
    }

    override fun seek(): Long? {
        return audioElement.currentTime.toLong()
    }

    override fun mediaDuration(): Long? {
        return audioElement.duration.toLong()
    }

    override fun setTime(time: Long) {
//        mediaPlayer?.controls()?.setTime(time)
        audioElement.currentTime = time.toDouble()
    }

    override fun release() {
    }
}
