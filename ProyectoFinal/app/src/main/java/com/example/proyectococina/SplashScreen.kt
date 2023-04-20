package com.example.proyectococina

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.runtime.*
import androidx.navigation.NavController
import android.widget.VideoView
import android.widget.MediaController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.proyectococina.R

@Composable
fun SplashScreen(navController: NavController) {
    var videoIsPlaying by remember { mutableStateOf(true) }

    if (videoIsPlaying) {
        AndroidView(factory = { context ->
            VideoView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                setMediaController(MediaController(context))
                setVideoPath("android.resource://" + context.packageName + "/" + R.raw.splashscreen)
                setOnPreparedListener { mediaPlayer ->
                    mediaPlayer.isLooping = true
                    start()
                }
                setOnCompletionListener {
                    videoIsPlaying = false
                    navController.navigate("main_activity")
                }
            }
        }, update = { videoView ->
            // No es necesario hacer nada aquí
        }, modifier = Modifier.fillMaxSize())
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFF009688),
                            Color(0xFF006064),
                            Color.White
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Lista cocina",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Green
            )
        }
    }
}

