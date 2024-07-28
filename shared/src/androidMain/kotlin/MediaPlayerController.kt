// Thanks to https://github.com/SEAbdulbasit/MusicApp-KMP/

import android.net.Uri
import android.provider.DocumentsContract
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

    private val songMap: MutableMap<String, String> = mutableMapOf()
    private val songsList: MutableList<String> = mutableListOf()
    private var currentSongIndex: Int? = null

    fun getFileFromTreeUri(uri: Uri): DocumentFile? {
        val docFile = DocumentFile.fromTreeUri(platformContext.applicationContext, uri)
        return docFile
    }

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

//        return tempAudioList
        songsList.clear()
        songsList.addAll(tempAudioList)
        currentSongIndex = if (songsList.isNotEmpty()) 0 else null
        return songsList
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

    actual suspend fun loadPlaylists(): Map<String, List<String>> {
//        val safeRootDir = platformContext.rootDir ?: return emptyMap() // Handle null case if necessary
//        val folderName = safeRootDir.substringAfter(":") // Gets the path after 'primary:'
//        val queryUri = when {
//            safeRootDir.startsWith("/tree/primary") -> MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
//            safeRootDir.startsWith("/tree/secondary") -> MediaStore.Audio.Media.EXTERNAL_CONTENT_URI // Adjust as needed for secondary storage
//            else -> MediaStore.Audio.Media.EXTERNAL_CONTENT_URI // Default URI
//        }
//        val queryUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
//        val projection = arrayOf(
//            MediaStore.Audio.AudioColumns.DATA,
//            MediaStore.Audio.AudioColumns.RELATIVE_PATH,
//            // Not using these, but maybe they'll be useful later
//            MediaStore.Audio.AudioColumns.ALBUM,
//            MediaStore.Audio.ArtistColumns.ARTIST
//        )
//        val selection = "${MediaStore.Audio.Media.DATA} LIKE ? AND ${MediaStore.Audio.Media.DATA} NOT LIKE ?"
//        val selectionArgs = arrayOf(
//            "/storage/emulated/0/Music/%",  // Include in one subdir deep
////            "$folderName/%/%/%" // Exclude files more than one subdir deep
//        )
//        val cursor = platformContext.applicationContext.contentResolver.query(queryUri, projection, selection, selectionArgs, null)
//        println("here playlists")
//        val resultMap = mutableMapOf<String, MutableList<String>>()
//        cursor?.use {
//            while (it.moveToNext()) {
//                val path = it.getString(0)
//                println(path)
//                val name = path.substringAfterLast("/")
//                val parentDir = path.substringBeforeLast("/")
//
//                // Extract the directory name from the path
//                val dirName = parentDir.substringAfterLast("/")
//
//                // Populate the map
//                if (dirName.isNotEmpty()) {
//                    if (resultMap.containsKey(dirName)) {
//                        resultMap[dirName]?.add(name)
//                    } else {
//                        resultMap[dirName] = mutableListOf(name)
//                    }
//                }
//            }
//        }


        // fuck you android studio >:(
        // crashing at each compile in the final hours....
        return mapOf(
            "playlist" to listOf(
                "first death.mp3",
                "Geoxor_-_Aether.mp3",
                "Go-Getters.mp3"
            ),
            "vocaloid" to listOf(
                "[60fps Full風] Sweet Devil - Hatsune Miku 初音ミク Project DIVA Arcade English lyrics Romaji subtitles-Gu0luN8SrIs.mp3",
                "Rolling Girl (ローリンガール) feat. Hatsune Miku _ Project DIVA Arcade Future Tone.mp3",
                "[1080P Full風] 千本桜 Senbonzakura 'One Thousand Cherry Trees'- 初音ミク Hatsune Miku DIVA English Romaji.mp3",
                "wowaka 『アンノウン・マザーグース』feat. 初音ミク _ wowaka - Unknown Mother-Goose (Official Video) ft. Hatsune Miku.mp3"
            ),
            "Ado" to listOf(
                "【Ado】 唱.mp3",
                "【Ado】逆光（ウタ from ONE PIECE FILM RED）-gt-v_YCkaMY.mp3",
                "【Ado】unravel 歌いました.mp3",
                "【Ado】新時代 (ウタ from ONE PIECE FILM RED).mp3",
                "【Ado】ウタカタララバイ（ウタ from ONE PIECE FILM RED）.mp3",
                "【Ado】ダーリンダンス 歌いました.mp3",
                "【Ado】踊.mp3",
                "【Ado】阿修羅ちゃん.mp3",
                "【Ado】うっせぇわ.mp3"
            ),
            "bangers" to listOf(
                "Voices of the Chord.mp3",
                "first death.mp3",
                "Giga - 'Ready Steady' ft. 初音ミク・鏡音リン・鏡音レン【MV】.mp3",
                "DECO＊27 - シンデレラ feat. 初音ミク [adGhT_-JbZI].mp3"
            )
        )
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

