import android.content.Context
import android.provider.MediaStore

class AndroidLibrary(private val rootDir: String, val applicationContext: Context): Library {
    private val songsList: MutableList<String> = mutableListOf()
    private var currentSongIndex: Int? = null
    override suspend fun getSongs(): List<String> {
        check(rootDir.startsWith("/tree/primary:")) { "Only directories on primary storage are supported, invalid dir: $rootDir" }
        val folderName = rootDir.substringAfter(":") // Gets the path after 'primary:'
        val queryUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

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
        val cursor = applicationContext.contentResolver.query(queryUri, projection, selection, selectionArgs, null)

        cursor?.use {
            while (it.moveToNext()) {
                val path = it.getString(0)
                val name = path.substringAfterLast("/")
                tempAudioList.add(path)
            }
        }

        songsList.clear()
        songsList.addAll(tempAudioList)
        currentSongIndex = if (songsList.isNotEmpty()) 0 else null
        return songsList
    }
    override suspend fun getPlaylists(): Map<String, List<String>> {
//        val folderName = rootDir.substringAfter(":") // Gets the path after 'primary:'
//        val queryUri = when {
//            rootDir.startsWith("/tree/primary") -> MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
//            rootDir.startsWith("/tree/secondary") -> MediaStore.Audio.Media.EXTERNAL_CONTENT_URI // Adjust as needed for secondary storage
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
    override fun nextTrack() {
        if (songsList.isNotEmpty() && currentSongIndex != null) {
            currentSongIndex = (currentSongIndex!! + 1) % songsList.size
        }
    }

    override fun previousTrack() {
        if (songsList.isNotEmpty() && currentSongIndex != null) {
            currentSongIndex = (currentSongIndex!! - 1 + songsList.size) % songsList.size
        }
    }

    override fun getCurrentSong(): String? {
        return currentSongIndex?.let { songsList[it] }
    }

    override fun setCurrentSongIdx(idx: Int) {
        currentSongIndex = idx
    }
}