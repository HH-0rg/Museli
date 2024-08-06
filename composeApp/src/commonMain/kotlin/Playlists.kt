import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun ExpandableSection(
    directory: String,
    songs: List<String>,
    navController: NavHostController,
    mediaPlayerController: MediaPlayerController,
    library: Library,
    songsList: List<String>
) {
    var isExpanded by remember { mutableStateOf(false) }
    var height = if (isExpanded) 300.dp else 70.dp

    Column(
        modifier = Modifier
            .height(height)
            .padding(16.dp),
    ) {
        Text(
            text = directory,
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Gray.copy(alpha = 0.1f))
                .padding(8.dp)
                .clickable { isExpanded = !isExpanded
                }
        )

        if (isExpanded) {
            LazyColumn {
                itemsIndexed(songs) { idx, song ->
                    Text(text = if (song.length > 50) "* ${song.take(50)}..." else "* ${song}",
                        color = Color.White,
                        modifier = Modifier
                            .clickable {
                                library.setCurrentSongIdx(songsList.indexOf(song))
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

@Composable
@Preview
fun PlayLists(
    mediaPlayerController: MediaPlayerController,
    navController: NavHostController,
    library: Library
) {
    var songsList by remember { mutableStateOf(emptyList<String>()) }
    var playlists by remember { mutableStateOf<Map<String, List<String>>>(emptyMap()) }

    LaunchedEffect(Unit) {
        songsList = library.getSongs()
        playlists = library.getPlaylists()
    }

    return Scaffold(
        backgroundColor = Color.Black
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize().height(500.dp)
                .padding(16.dp),
        ) {
            Text(
                text = "Songs in the playlist",
                color = Color.White,
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(playlists.entries.toList()) { (directory, songs) ->
                    ExpandableSection(directory = directory, songs = songs, navController, mediaPlayerController, library, songsList)
                }
            }
            LazyColumn {
                items(songsList.size) { song ->
                    Text(text = if (songsList[song].length > 50) "* ${songsList[song].take(50)}..." else "* ${songsList[song]}",
                        color = Color.White,
                        modifier = Modifier
                            .clickable {
                                library.setCurrentSongIdx(song)
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