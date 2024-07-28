package org.hh.museli

import App
import MediaPlayerController
import PlatformContext
import android.Manifest.permission.READ_MEDIA_AUDIO
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import io.github.vinceglb.filekit.core.FileKit
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (!isGranted) {
            // Permission denied
            handlePermissionDenied()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (ContextCompat.checkSelfPermission(
                this,
                READ_MEDIA_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(READ_MEDIA_AUDIO)
        }

        lifecycleScope.launch {
            FileKit.init(this@MainActivity)

            setContent {
                App(mediaPlayerController = MediaPlayerController(PlatformContext(applicationContext, null)))
            }
        }
    }

    private fun handlePermissionDenied() {
        Toast.makeText(this, "Permission is required to access music files.", Toast.LENGTH_LONG).show()
        finish() // Quit the application
    }
}

//@Preview
//@Composable
//fun AppAndroidPreview() {
//    App()
//}