import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(songsList: Array<String> = arrayOf("Superman", "batman", "Shaktiman", "Hanuman")) {
    MaterialTheme {

//        val navController = rememberNavController()

//        Scaffold(
//            content = { padding ->
//                AppNavHost(navController = navController)
//            }
//        )

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
//            MusicPlayer()

            LazyColumn {
                items(songsList.size) { song ->
                    Text(songsList[song])
                }
            }
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding()
                ) {
                    BottomAppBar(
                        modifier = Modifier.align(Alignment.BottomCenter)
                    ) {
                        // BottomAppBar content here
                        IconButton(onClick = { /* Do something */ }) {
                            Icon(Icons.Default.Home, contentDescription = "Home")
                        }
                        Spacer(Modifier.weight(1f, true))
                        IconButton(onClick = { /* Do something */ }) {
                            Icon(Icons.Default.PlayArrow, contentDescription = "Player")
                        }
                        Spacer(Modifier.weight(1f, true))
                        IconButton(onClick = { /* Do something */ }) {
                            Icon(Icons.Default.List, contentDescription = "Playlists")
                        }
                        Spacer(Modifier.weight(1f, true))
                        IconButton(onClick = { /* Do something */ }) {
                            Icon(Icons.Default.Settings, contentDescription = "Settings")
                        }
                    }
                }
            }
        }
    }
}