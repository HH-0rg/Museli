import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import io.github.vinceglb.filekit.core.FileKit
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


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
        for ((directory, songs) in mediaPlayerController.loadPlaylists()) {
            println("Directory: $directory")
            for (song in songs) {
                println("  Song: $song")
            }
        }
        songsList = loadedSongs
    }

//    return Scaffold(backgroundColor = Color.Black) {
//        Column {
//            Text("Songs in the playlist", color = Color.White)
//            LazyColumn {
//                items(songsList.size) { song ->
//                    TextButton(onClick = {
//                        mediaPlayerController.setCurrentSongIdx(song)
//                        navController.navigate("music_player")
//                    }) {
//                        Text(songsList[song], color = Color.White)
//                    }
//                }
//            }
//        }
//    }
    return Scaffold(
        backgroundColor = Color.Black
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            Text(
                text = "Songs in the playlist",
                color = Color.White,
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn {
                items(songsList.size) { song ->
                    Text(text = if (songsList[song].length > 50) "* ${songsList[song].take(50)}..." else "* ${songsList[song]}",
                        color = Color.White,
                        modifier = Modifier
                            .clickable {
                                mediaPlayerController.setCurrentSongIdx(song)
                                navController.navigate("music_player")
                            }
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)

                    )
                }
            }
        }
    }

}