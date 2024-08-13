import uk.co.caprica.vlcj.factory.discovery.NativeDiscovery
import uk.co.caprica.vlcj.player.base.MediaPlayer
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent


class DesktopController: MediaPlayerController {
    private var mediaPlayer: MediaPlayer

    init {
        NativeDiscovery().discover()
        AudioPlayerComponent().also { mediaPlayer = it.mediaPlayer() }
    }

    override fun prepare(
        songUri: String, listener: MediaPlayerListener
    ) {
        mediaPlayer.events()?.addMediaPlayerEventListener(object : MediaPlayerEventAdapter() {
            override fun mediaPlayerReady(mediaPlayer: MediaPlayer?) {
                super.mediaPlayerReady(mediaPlayer)
                listener.onReady()
            }

            override fun finished(mediaPlayer: MediaPlayer?) {
                super.finished(mediaPlayer)
                listener.onAudioCompleted()
            }

            override fun error(mediaPlayer: MediaPlayer?) {
                super.error(mediaPlayer)
                listener.onError()
            }
        })

        if (mediaPlayer.status()?.isPlaying == true) {
            mediaPlayer.controls()?.stop()
        }

        mediaPlayer.media()?.play(songUri)
    }

    override fun start() {
        mediaPlayer.controls()?.start()
    }

    override fun pause() {
        mediaPlayer.controls()?.pause()
    }

    override fun stop() {
        mediaPlayer.controls()?.stop()
    }

    override fun isPlaying(): Boolean {
        return mediaPlayer.status()?.isPlaying ?: false
    }

    override fun seek(): Long? {
        return mediaPlayer.status()?.time()
    }

    override fun mediaDuration(): Long? {
        return mediaPlayer.status()?.length()
    }

    override fun setTime(time: Long) {
        mediaPlayer.controls()?.setTime(time)
    }
    
    override fun release() {
        mediaPlayer.release()
    }
}