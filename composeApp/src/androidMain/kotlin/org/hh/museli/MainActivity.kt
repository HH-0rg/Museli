package org.hh.museli

import AndroidLibrary
import App
import Library
import AndroidController
import android.Manifest.permission.READ_MEDIA_AUDIO
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import io.github.vinceglb.filekit.core.FileKit
import kotlinx.coroutines.launch
import nullError

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

        val libraryProvider: suspend () -> Library = {
            val dir = FileKit.pickDirectory()?.path
            check(dir != null, ::nullError)
            check(dir.startsWith("/tree/primary:")) { "only directories on primary storage are supported, invalid dir: $dir" }
            AndroidLibrary(dir, applicationContext)
        }

        lifecycleScope.launch {
            FileKit.init(this@MainActivity)

            setContent {
                App(mediaPlayerController = AndroidController(applicationContext), libraryProvider)
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