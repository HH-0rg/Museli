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


@Composable
@Preview
fun App(songsList: List<String> = listOf("Superman", "batman", "Shaktiman", "Hanuman")) {
    var navController: NavHostController = rememberNavController()

    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }

        Scaffold(
            bottomBar = {
                BottomAppBar(
                    backgroundColor = Color.Black,
                    cutoutShape = CircleShape,
                    contentPadding = PaddingValues(8.dp)
                ) {
                    IconButton(onClick = { navController.navigate("home_page") }) {
                        Icon(Icons.Filled.Home, contentDescription = "Home", Modifier.background(Color.White))
                    }
                    Spacer(Modifier.weight(1f, true))
                    FloatingActionButton(onClick = { navController.navigate("music_player") }) {
                        Icon(Icons.Filled.PlayArrow, contentDescription = "Player", Modifier.background(Color.Green))
                    }
                    Spacer(Modifier.weight(1f, true))
                    IconButton(onClick = { navController.navigate("playlist_screen") }) {
                        Icon(Icons.Filled.List, contentDescription = "Playlists", Modifier.background(Color.White))
                    }
                }
            },
            content = {
                NavHost(
                    navController = navController,
                    startDestination = "music_player",

                    ) {
                    composable(route = "music_player") {
                        MusicPlayer()
                    }
                    composable(route = "home_page") {
                        HomePage()
                    }
                    composable(route = "playlist_screen") {
                        PlayLists(songsList)
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