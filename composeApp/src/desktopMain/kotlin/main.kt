import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.github.vinceglb.filekit.core.FileKit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


fun main(args: Array<String>) = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Museli",
    ) {
        val mediaPlayerController = MediaPlayerController(PlatformContext(null))

        App(mediaPlayerController) { FileKit.pickDirectory()?.path }

//      This code allows you to get data from a server
//        val scope = rememberCoroutineScope()
//        val songs = remember { mutableStateListOf<String>() }
//
//        LaunchedEffect(Unit) {
//            val fetchedSongs = getSongsRemote("http://localhost:8080")
//            songs.addAll(fetchedSongs)
//        }
//
//        App(songs)
    }
}