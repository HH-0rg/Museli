import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.filled.*
import io.github.vinceglb.filekit.core.FileKit

@Composable
@Preview
fun MusicPlayer(mediaPlayerController: MediaPlayerController, song: String?) {

    var songsList: List<String> = emptyList()
    var rootDir_: String? = null

    var isPlaying by remember { mutableStateOf(false) }

    var currentPosition by mutableStateOf(0f)
    var maxDuration by mutableStateOf(1f)
    var isShuffling by mutableStateOf(false)
    var isRepeating by mutableStateOf(false)

    LaunchedEffect(Unit) {
        if (song == null) {
            println("No song to play")
        } else {

            mediaPlayerController.prepare(song, listener = object : MediaPlayerListener {
                override fun onReady() {
                    println("ready")
                }

                override fun onAudioCompleted() {
                    println("audio completed")
                }

                override fun onError() {
                    println("error")
                }
            })
            mediaPlayerController.start()
        }
    }
    println(mediaPlayerController.seek())

    Scaffold(backgroundColor = Color.White) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "$song", fontSize = 24.sp, style = MaterialTheme.typography.h6)
            Text(text = "${mediaPlayerController.seek()}", fontSize = 24.sp, style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(8.dp))
            Text("${mediaPlayerController.mediaDuration()}")
            Slider(
                value = currentPosition,
                onValueChange = {
                    currentPosition = it
//                    mediaPlayer?.seekTo(it.toInt())
                },
                valueRange = 0f..maxDuration
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { if (mediaPlayerController.isPlaying()) mediaPlayerController.pause() else mediaPlayerController.start() }) {
                    Icon(
                        imageVector = if (mediaPlayerController.isPlaying()) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                        contentDescription = if (isPlaying) "Pause" else "Play"
                    )
                }
                IconButton(onClick = { mediaPlayerController.start() }) {
                    Icon(imageVector = Icons.Filled.SkipNext, contentDescription = "Next")
                }
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = if (isShuffling) Icons.Filled.Shuffle else Icons.Filled.Shuffle,
                        contentDescription = if (isShuffling) "Shuffle On" else "Shuffle Off"
                    )
                }
                IconButton(onClick = {mediaPlayerController.setTime(30000)}) {
                    Icon(
                        imageVector = if (isRepeating) Icons.Filled.RepeatOne else Icons.Filled.Repeat,
                        contentDescription = if (isRepeating) "Repeat One" else "Repeat"
                    )
                }
            }
        }
    }


}
