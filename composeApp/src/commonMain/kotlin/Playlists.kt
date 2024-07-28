import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import io.github.vinceglb.filekit.core.FileKit


@Composable
@Preview
fun PlayLists(
    mediaPlayerController: MediaPlayerController,
    navController: NavHostController,
    rootPicker: suspend () -> String?
) {
    var songsList by remember { mutableStateOf(emptyList<String>()) }
    var rootDir by remember { mutableStateOf<String?>(GlobalState.rootDirectory) }

    LaunchedEffect(Unit) {
        if (rootDir == null) {
            rootDir = rootPicker()
            rootDir?.let {
                mediaPlayerController.setRoot(it)
                GlobalState.rootDirectory = it.toString()
            }
        }
        val loadedSongs = mediaPlayerController.loadSongList()
        songsList = loadedSongs
    }

    return Scaffold(backgroundColor = Color.Black) {
        Column {
            Text("Songs in the playlist", color = Color.White)
            LazyColumn {
                items(songsList.size) { song ->
                    TextButton(onClick = { navController.navigate("music_player/${songsList[song]}") }) {
                        Text(songsList[song], color = Color.White)
                    }
                }
            }
        }
    }
}