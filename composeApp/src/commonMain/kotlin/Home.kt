import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Color

@Composable
@Preview
fun HomePage() {
    var isPlaying by remember { mutableStateOf(false) }

     return Scaffold (backgroundColor = Color.Black) {
        Column {
            Text(" Home Page", color = Color.White)
        }
    }
}
