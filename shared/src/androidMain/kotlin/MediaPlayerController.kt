// Thanks to https://github.com/SEAbdulbasit/MusicApp-KMP/

import android.net.Uri
import android.provider.MediaStore
import androidx.documentfile.provider.DocumentFile
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.Player.STATE_ENDED
import androidx.media3.common.Player.STATE_READY
import androidx.media3.exoplayer.ExoPlayer
import java.io.File

actual class MediaPlayerController actual constructor(private val platformContext: PlatformContext) {
    val player = ExoPlayer.Builder(platformContext.applicationContext).build()

    actual fun prepare(songUri: String, listener: MediaPlayerListener) {

        val mediaItem = MediaItem.fromUri(Uri.fromFile(File(songUri)))
        player.addListener(object : Player.Listener {
            override fun onPlayerError(error: PlaybackException) {
                super.onPlayerError(error)
                listener.onError()
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                when (playbackState) {
                    STATE_READY -> listener.onReady()
                    STATE_ENDED -> listener.onAudioCompleted()
                }
            }

            override fun onPlayerErrorChanged(error: PlaybackException?) {
                super.onPlayerErrorChanged(error)
                listener.onError()
            }
        })
        if (mediaItem != null) {
            player.setMediaItem(mediaItem)
            player.prepare()
            player.play()
        }
    }

    actual fun start() {
        player.play()
    }

    actual fun pause() {
        if (player.isPlaying)
            player.pause()
    }

    actual fun stop() {
        player.stop()
    }

    actual fun release() {
        player.release()
    }

    actual fun seek(): Long? {
        println(player.currentPosition)
        println(player.duration)
        println(player.contentDuration)
        println(player.contentPosition)
        println(player.currentMediaItem)
        println("player.currentMediaItem")


        return  player.contentPosition
    }

    actual fun mediaDuration(): Long? {
        return player.duration
    }

    actual fun setTime(time: Long) {
        player.seekTo(time)
    }

    actual fun isPlaying(): Boolean {
        return player.isPlaying
    }
}

