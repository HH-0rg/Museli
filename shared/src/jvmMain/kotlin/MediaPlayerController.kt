// Thanks to https://github.com/SEAbdulbasit/MusicApp-KMP/

import uk.co.caprica.vlcj.factory.discovery.NativeDiscovery
import uk.co.caprica.vlcj.player.base.MediaPlayer
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent


actual class MediaPlayerController actual constructor(val platformContext: PlatformContext) {
    private var mediaPlayer: MediaPlayer? = null
    private var listener: MediaPlayerListener? = null

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

    actual fun prepare(
        songUri: String, listener: MediaPlayerListener
    ) {
        if (mediaPlayer == null) {
            initMediaPlayer()
            this.listener = listener
        }

        if (mediaPlayer?.status()?.isPlaying == true) {
            mediaPlayer?.controls()?.stop()
        }

        mediaPlayer?.media()?.play(songUri)
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
    
    actual fun release() {
        mediaPlayer?.release()
    }
}