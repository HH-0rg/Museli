import android.content.Context
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.Player.STATE_ENDED
import androidx.media3.common.Player.STATE_READY
import androidx.media3.exoplayer.ExoPlayer
import java.io.File

class AndroidController(private val context: Context): MediaPlayerController {
    val player = ExoPlayer.Builder(context).build()

    override fun prepare(songUri: String, listener: MediaPlayerListener) {

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

    override fun start() {
        player.play()
    }

    override fun pause() {
        if (player.isPlaying)
            player.pause()
    }

    override fun stop() {
        player.stop()
    }

    override fun release() {
        player.release()
    }

    override fun seek(): Long? {
        println(player.currentPosition)
        println(player.duration)
        println(player.contentDuration)
        println(player.contentPosition)
        println(player.currentMediaItem)
        println("player.currentMediaItem")


        return  player.contentPosition
    }

    override fun mediaDuration(): Long? {
        return player.duration
    }

    override fun setTime(time: Long) {
        player.seekTo(time)
    }

    override fun isPlaying(): Boolean {
        return player.isPlaying
    }
}

