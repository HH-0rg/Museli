import android.content.Context
import android.provider.MediaStore

fun getSongs(context: Context): List<String> {
    val folderName = androidRoot
    val tempAudioList = mutableListOf<String>()
    val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
    val projection = arrayOf(
        MediaStore.Audio.AudioColumns.DATA,
        // Not using these, but maybe they'll be useful later
        MediaStore.Audio.AudioColumns.ALBUM,
        MediaStore.Audio.ArtistColumns.ARTIST
    )
    val selection = "${MediaStore.Audio.Media.DATA} LIKE ? AND ${MediaStore.Audio.Media.DATA} NOT LIKE ?"
    val selectionArgs = arrayOf(
        "$folderName%",  // Include only files in the specific folder
        "$folderName%/%" // Exclude files in subdirectories
    )
    val cursor = context.contentResolver.query(uri, projection, selection, selectionArgs, null)

    cursor?.use {
        while (it.moveToNext()) {
            val path = it.getString(0)
            val name = path.substringAfterLast("/")
            tempAudioList.add(name)
        }
    }

    return tempAudioList
}