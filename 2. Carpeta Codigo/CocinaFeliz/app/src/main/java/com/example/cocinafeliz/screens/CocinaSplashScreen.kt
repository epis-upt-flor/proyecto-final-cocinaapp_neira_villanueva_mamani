package com.example.cocinafeliz.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cocinafeliz.R
import com.example.cocinafeliz.navigation.CocinaScreens
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun CocinaSplashScreen(navController: NavController){
    val eleganciaFontFamily = FontFamily(Font(R.font.elegancia))
    val scale = remember{
        androidx.compose.animation.core.Animatable(0f)
    }
    LaunchedEffect(key1 = true){
        scale.animateTo(targetValue = 0.9f, animationSpec = tween(durationMillis = 800, easing = {
            OvershootInterpolator(8f).getInterpolation(it)
        }))
        //Unos segundos mostrara este Composable y luego pasara a la siguietne ventana:
        delay(3500L)
        //Linea de codigo que permite navegar a login Screen:

        if(FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()){
            navController.navigate(CocinaScreens.LoginScreen.name)
        }
        else{
            navController.navigate(CocinaScreens.CocinaHomeScreen.name)
        }
    }
    val color = MaterialTheme.colorScheme.primary
    Surface(modifier = Modifier
        .padding(15.dp)
        .size(330.dp)
        .scale(scale.value),
        shape = CircleShape,
        color = Color.Black,
        border = BorderStroke(width = 2.dp, color = color)
    ) {
        Column(modifier = Modifier
            .padding(1.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Text("Cocina Feliz",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = eleganciaFontFamily,
                    fontSize = 80.sp
                ),
                color = color.copy(alpha = 0.5f)
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text("RECETAS AL INSTANTE",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = eleganciaFontFamily,
                    fontSize = 35.sp
                ),
                color = color.copy(alpha = 0.5f)
            )
        }

    }
}