import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import io.github.vinceglb.filekit.core.FileKit
import kotlinx.browser.document
import kotlinx.browser.window

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val root = if (window.location.toString().startsWith("http://localhost")) {
        "http://localhost:8081/"
    } else {
        "/"
    }

    ComposeViewport(document.body!!) {
        App(MediaPlayerController(PlatformContext(root))) { root }
    }
}