// Thanks to https://github.com/SEAbdulbasit/MusicApp-KMP/

import android.net.Uri
import android.provider.MediaStore
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.Player.STATE_ENDED
import androidx.media3.common.Player.STATE_READY
import androidx.media3.exoplayer.ExoPlayer
import java.io.File

actual class MediaPlayerController actual constructor(private val platformContext: PlatformContext) {
    val player = ExoPlayer.Builder(platformContext.applicationContext).build()

    private val songMap: MutableMap<String, String> = mutableMapOf()

    actual suspend fun loadSongList(): List<String> {
        val safeRootDir = platformContext.rootDir ?: return emptyList() // Handle null case if necessary
        val folderName = safeRootDir.substringAfter(":") // Gets the path after 'primary:'
        val queryUri = when {
            safeRootDir.startsWith("/tree/primary") -> MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            safeRootDir.startsWith("/tree/secondary") -> MediaStore.Audio.Media.EXTERNAL_CONTENT_URI // Adjust as needed for secondary storage
            else -> MediaStore.Audio.Media.EXTERNAL_CONTENT_URI // Default URI
        }

        val tempAudioList = mutableListOf<String>()
        val projection = arrayOf(
            MediaStore.Audio.AudioColumns.DATA,
            MediaStore.Audio.AudioColumns.RELATIVE_PATH,
            // Not using these, but maybe they'll be useful later
            MediaStore.Audio.AudioColumns.ALBUM,
            MediaStore.Audio.ArtistColumns.ARTIST
        )
        val selection = "${MediaStore.Audio.Media.RELATIVE_PATH} LIKE ? AND ${MediaStore.Audio.Media.RELATIVE_PATH } NOT LIKE ?"
        val selectionArgs = arrayOf(
            "$folderName/%",  // Include only files in the specific folder
            "$folderName/%/%" // Exclude files in subdirectories
        )
        val cursor = platformContext.applicationContext.contentResolver.query(queryUri, projection, selection, selectionArgs, null)

        cursor?.use {
            while (it.moveToNext()) {
                val path = it.getString(0)
                val name = path.substringAfterLast("/")
                songMap[name] = path // Populate the map
                tempAudioList.add(name)
            }
        }

        return tempAudioList
    }
    actual fun setRoot(newRoot: String) {
        platformContext.rootDir = newRoot
    }
    actual fun prepare(song: String, listener: MediaPlayerListener) {

        val mediaItem = songMap[song]?.let { MediaItem.fromUri(Uri.fromFile(File(it))) }
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

