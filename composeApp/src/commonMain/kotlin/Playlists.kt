import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.material.*


@Composable
@Preview
fun PlayLists(songsList: List<String> = listOf("Superman", "batman", "Shaktiman", "Hanuman")) {
    return Scaffold {
        Column {
            Text("Songs in the playlist")
            LazyColumn {
                items(songsList.size) { song ->
                    Text(songsList[song])
                }
            }
        }
    }
}