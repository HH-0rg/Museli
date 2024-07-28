// Thanks to https://github.com/SEAbdulbasit/MusicApp-KMP/

import kotlinx.browser.document
import org.w3c.dom.HTMLAudioElement

actual class MediaPlayerController actual constructor(val platformContext: PlatformContext) {
    private val audioElement = document.createElement("audio") as HTMLAudioElement
    private val songsList: MutableList<String> = mutableListOf()
    private var currentSongIndex: Int? = null

    actual fun setRoot(newRoot: String) {
        // We don't want to change root
        // platformContext.rootUrl = newRoot
    }

    actual suspend fun loadSongList(): List<String> {

        var tempSongsList = getSongsRemote(platformContext.rootUrl)
        songsList.clear()
        songsList.addAll(tempSongsList)
        currentSongIndex = if (songsList.isNotEmpty()) 0 else null
        return songsList

    }

    actual suspend fun loadPlaylists(): Map<String, List<String>> {
        return getPlaylistsRemote(platformContext.rootUrl)
    }

    actual fun prepare(
        song: String,
        listener: MediaPlayerListener
    ) {
        audioElement.src = "${platformContext.rootUrl}${SongEp}/${song}"
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

    actual fun nextTrack() {
        if (songsList.isNotEmpty() && currentSongIndex != null) {
            currentSongIndex = (currentSongIndex!! + 1) % songsList.size
        }
    }

    actual fun previousTrack() {
        if (songsList.isNotEmpty() && currentSongIndex != null) {
            currentSongIndex = (currentSongIndex!! - 1 + songsList.size) % songsList.size
        }
    }

    actual fun getCurrentSong(): String? {
        return currentSongIndex?.let { songsList[it] }
    }

    actual fun setCurrentSongIdx(idx: Int) {
        currentSongIndex = idx
    }

}
