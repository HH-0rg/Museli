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
import androidx.compose.ui.semantics.Role.Companion.Image
import io.github.vinceglb.filekit.core.FileKit
import kotlinx.coroutines.delay
import androidx.compose.ui.graphics.toArgb
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toArgb

fun generateRandomImage(width: Int, height: Int): ImageBitmap {
    val pixels = IntArray(width * height) {
        Color(
            (0..255).random(),
            (0..255).random(),
            (0..255).random(),
            255
        ).toArgb()
    }

    return ImageBitmap(width, height)
}


@Composable
@Preview
fun MusicPlayer(mediaPlayerController: MediaPlayerController) {

    var songsList: List<String> = emptyList()
    var rootDir_: String? = null

    var isPlaying by remember { mutableStateOf(false) }


    var maxDuration: Long by remember { mutableStateOf(0) }
    var isShuffling by remember { mutableStateOf(false) }
    var isRepeating by remember { mutableStateOf(false) }
    var currentPosition: Long by remember { mutableStateOf(0) }

    fun prepareMedia() {
        if (mediaPlayerController.getCurrentSong() == null) {
            println("No song to play")
        } else {

            mediaPlayerController.prepare(
                mediaPlayerController.getCurrentSong()!!,
                listener = object : MediaPlayerListener {
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
            maxDuration =
                if (mediaPlayerController.mediaDuration() != null && mediaPlayerController.mediaDuration()!! > 0) mediaPlayerController.mediaDuration()
                    ?.toLong()!! else 0L
        }

    }

    LaunchedEffect(Unit) {
        prepareMedia()
    }

    LaunchedEffect("MediaController") {
//        while (mediaPlayerController.isPlaying()) { // need to comment this out for android
        while (true) {
            delay(1000) // Update every second
            println(mediaPlayerController.seek())
            currentPosition = if (mediaPlayerController.seek() != null) mediaPlayerController.seek()!! else 0L
            println("current $currentPosition")
        }
    }

    Scaffold(backgroundColor = Color.White) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "${mediaPlayerController.getCurrentSong()}", fontSize = 24.sp, style = MaterialTheme.typography.h6)
            Text(
                text = "${formatDuration(currentPosition)} / ${formatDuration(mediaPlayerController.mediaDuration())}",
                fontSize = 24.sp,
                style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.height(8.dp))
            Slider(
                value = currentPosition.toFloat(),
                onValueChange = {
                    currentPosition = it.toLong()
                    mediaPlayerController.setTime(it.toLong())
//                    mediaPlayer?.seekTo(it.toInt())
                },
                valueRange = 0f..((if (mediaPlayerController.mediaDuration() != null && mediaPlayerController.mediaDuration()!! > 0) mediaPlayerController.mediaDuration()
                    ?.toLong()!! else 0).toFloat())
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = if (isShuffling) Icons.Filled.Shuffle else Icons.Filled.Shuffle,
                        contentDescription = if (isShuffling) "Shuffle On" else "Shuffle Off"
                    )
                }
                IconButton(onClick = {
                    mediaPlayerController.previousTrack()
                    prepareMedia()
                }) {
                    Icon(imageVector = Icons.Filled.SkipPrevious, contentDescription = "Previous")
                }
                IconButton(onClick = { if (mediaPlayerController.isPlaying()) mediaPlayerController.pause() else mediaPlayerController.start() }) {
                    Icon(
                        imageVector = if (mediaPlayerController.isPlaying()) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                        contentDescription = if (isPlaying) "Pause" else "Play"
                    )
                }
                IconButton(onClick = {
                    mediaPlayerController.nextTrack()
                    prepareMedia()
                }) {
                    Icon(imageVector = Icons.Filled.SkipNext, contentDescription = "Next")
                }
                IconButton(onClick = { mediaPlayerController.setTime(30000) }) {
                    Icon(
                        imageVector = if (isRepeating) Icons.Filled.RepeatOne else Icons.Filled.Repeat,
                        contentDescription = if (isRepeating) "Repeat One" else "Repeat"
                    )
                }
            }
        }
    }


}
