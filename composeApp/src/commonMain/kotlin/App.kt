import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.jetbrains.compose.ui.tooling.preview.Preview

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.github.vinceglb.filekit.core.FileKit
import museli.composeapp.generated.resources.Res

object GlobalState {
    var rootDirectory: String? = null
}

@Composable
@Preview
fun App(
    mediaPlayerController: MediaPlayerController,
    makeLibrary: suspend () -> Library?
) {
    val navController: NavHostController = rememberNavController()
    var songsList: List<String> = emptyList()
    var currentSong: String? = null
    var library: Library? by remember { mutableStateOf(null) }
    LaunchedEffect(Unit) {
        library = makeLibrary()
    }

    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }

        Scaffold(
            bottomBar = {
                BottomAppBar(
                    backgroundColor = Color.Black,
                    cutoutShape = CircleShape,
                    contentColor = Color.White,
                    contentPadding = PaddingValues(8.dp)
                ) {
                    IconButton(onClick = {
                        navController.navigate("home_page")
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Home",
                            tint = Color.White // Ensure the icon color is white
                        )
                    }
                    Spacer(Modifier.weight(1f, true))
                    FloatingActionButton(
                        onClick = { navController.navigate("music_player") },
                        backgroundColor = Color.LightGray, // Custom background color
                        contentColor = Color.White // Custom icon color
                    ) {
                        Icon(Icons.Filled.PlayArrow, contentDescription = "Play")
                    }
                    Spacer(Modifier.weight(1f, true))
                    IconButton(onClick = { navController.navigate("playlist_screen") }) {
                        Icon(
                            imageVector = Icons.Filled.List,
                            contentDescription = "List",
                            tint = Color.White // Ensure the icon color is white
                        )
                    }
                }
            },
            content = {
                if (library != null) {
                    NavHost(
                        navController = navController,
                        startDestination = "home_page",

                        ) {
                        composable(
                            route = "music_player",
                        ) {
                            MusicPlayer(mediaPlayerController, library!!)
                        }
                        composable(route = "home_page") {
                            HomePage()
                        }
                        composable(route = "playlist_screen") {
                            PlayLists(mediaPlayerController, navController, library!!)
                        }
                    }
                }
            }
        )


//        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
////            MusicPlayer()
//
//            Column {
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding()
//                ) {
//                    BottomAppBar(
//                        modifier = Modifier.align(Alignment.BottomCenter),
//                    ) {
//                        // BottomAppBar content here
//                        IconButton(onClick = { navController.navigate("music_player") }) {
//                            Icon(Icons.Default.Home, contentDescription = "Home")
//                        }
//                        Spacer(Modifier.weight(1f, true))
//                        IconButton(onClick = { navController.navigate("playlist_screen") }) {
//                            Icon(Icons.Default.PlayArrow, contentDescription = "Player")
//                        }
//                        Spacer(Modifier.weight(1f, true))
//                        IconButton(onClick = { /* Do something */ }) {
//                            Icon(Icons.Default.List, contentDescription = "Playlists")
//                        }
//                        Spacer(Modifier.weight(1f, true))
//                        IconButton(onClick = { /* Do something */ }) {
//                            Icon(Icons.Default.Settings, contentDescription = "Settings")
//                        }
//                        FloatingActionButton(onClick = { navController.navigate("playlist_screen") }) {
//                            Icon(Icons.Default.PlayArrow, contentDescription = "Player")
//                        }
//                    }
//                }
//            }
//        }
    }
}