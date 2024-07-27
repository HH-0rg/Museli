import android.content.Context
import android.provider.MediaStore

fun getSongs(context: Context): Array<String> {
    val folderName = "/storage/emulated/0/Music/"
    val tempAudioList = mutableListOf<String>()
    val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
    val projection = arrayOf(
        MediaStore.Audio.AudioColumns.DATA,
        // not using these, but maybe they'll be useful later
        MediaStore.Audio.AudioColumns.ALBUM,
        MediaStore.Audio.ArtistColumns.ARTIST
    )
    val selection = "${MediaStore.Audio.Media.DATA} LIKE ?"
    val selectionArgs = arrayOf("%$folderName%")
    val cursor = context.contentResolver.query(uri, projection, selection, selectionArgs, null)

    cursor?.use {
        while (it.moveToNext()) {
            val path = it.getString(0)
            val name = path.substringAfterLast("/")
            tempAudioList.add(name)
        }
    }

    return tempAudioList.toTypedArray()
}