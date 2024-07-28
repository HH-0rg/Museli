// Thanks to https://github.com/SEAbdulbasit/MusicApp-KMP/

import uk.co.caprica.vlcj.factory.discovery.NativeDiscovery
import uk.co.caprica.vlcj.player.base.MediaPlayer
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter
import uk.co.caprica.vlcj.player.base.State
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent
import uk.co.caprica.vlcj.player.component.CallbackMediaPlayerComponent
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent
import java.io.File
import java.nio.file.Paths
import java.sql.Time
import java.util.Locale


actual class MediaPlayerController actual constructor(val platformContext: PlatformContext) {
    private var mediaPlayer: MediaPlayer? = null
    private var listener: MediaPlayerListener? = null
    private val songsList: MutableList<String> = mutableListOf()
    private var currentSongIndex: Int? = null

    private fun initMediaPlayer() {
        NativeDiscovery().discover()

        AudioPlayerComponent().also { mediaPlayer = it.mediaPlayer() }

        mediaPlayer?.events()?.addMediaPlayerEventListener(object : MediaPlayerEventAdapter() {
            override fun mediaPlayerReady(mediaPlayer: MediaPlayer?) {
                super.mediaPlayerReady(mediaPlayer)
                listener?.onReady()
            }

            override fun finished(mediaPlayer: MediaPlayer?) {
                super.finished(mediaPlayer)
                listener?.onAudioCompleted()
            }

            override fun error(mediaPlayer: MediaPlayer?) {
                super.error(mediaPlayer)
                listener?.onError()
            }
        })
    }

    actual suspend fun loadPlaylists(): Map<String, List<String>> {
        var songs = getPlaylists(platformContext.rootDir)
        return getPlaylists(platformContext.rootDir)
    }

    actual fun setRoot(newRoot: String) {
        platformContext.rootDir = newRoot
    }

    actual suspend fun loadSongList(): List<String> {
        var tempSongsList = getSongs(platformContext.rootDir)
        songsList.clear()
        songsList.addAll(tempSongsList)
        currentSongIndex = if (songsList.isNotEmpty()) 0 else null
        return songsList
    }

    actual fun prepare(
        song: String, listener: MediaPlayerListener
    ) {
        val fullPath = platformContext.rootDir?.let { "${it}/${song}" }
        if (mediaPlayer == null) {
            initMediaPlayer()
            this.listener = listener
        }

        if (mediaPlayer?.status()?.isPlaying == true) {
            mediaPlayer?.controls()?.stop()
        }

        mediaPlayer?.media()?.play(fullPath)
    }

    actual fun start() {
        mediaPlayer?.controls()?.start()
    }

    actual fun pause() {
        mediaPlayer?.controls()?.pause()
    }

    actual fun stop() {
        mediaPlayer?.controls()?.stop()
    }

    actual fun isPlaying(): Boolean {
        return mediaPlayer?.status()?.isPlaying ?: false
    }

    actual fun seek(): Long? {
        return mediaPlayer?.status()?.time()
    }

    actual fun mediaDuration(): Long? {
        return mediaPlayer?.status()?.length()
    }

    actual fun setTime(time: Long) {
        mediaPlayer?.controls()?.setTime(time)
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
    
    actual fun release() {
        mediaPlayer?.release()
    }

    private fun Any.mediaPlayer(): MediaPlayer {
        return when (this) {
            is CallbackMediaPlayerComponent -> mediaPlayer()
            is EmbeddedMediaPlayerComponent -> mediaPlayer()
            else -> throw IllegalArgumentException("You can only call mediaPlayer() on vlcj player component")
        }
    }

    private fun isMacOS(): Boolean {
        val os = System.getProperty("os.name", "generic").lowercase(Locale.ENGLISH)
        return os.indexOf("mac") >= 0 || os.indexOf("darwin") >= 0
    }
}