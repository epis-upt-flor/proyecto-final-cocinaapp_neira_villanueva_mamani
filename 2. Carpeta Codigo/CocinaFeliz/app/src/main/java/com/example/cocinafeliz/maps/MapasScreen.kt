package com.example.cocinafeliz.maps

import android.widget.Space
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cocinafeliz.R
import com.example.cocinafeliz.navigation.CocinaScreens
import com.example.cocinafeliz.screens.login.LoginScreenViewModel
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerInfoWindowContent
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

val laglorieta = LatLng(-18.013448, -70.260137)
val rest55 = LatLng(-18.015109, -70.260313)
val ajilimo = LatLng(-18.021139, -70.248531)

@Composable
fun MapasScreen(
    navController: NavController,
    viewModel: LoginScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
){

    val defaultCameraPosition = CameraPosition.fromLatLngZoom(laglorieta,11f)
    val cameraPositionState = rememberCameraPositionState{
        position = defaultCameraPosition
    }
    var isMapLoaded by remember {
        mutableStateOf(false)
    }
    Box(modifier = Modifier.fillMaxSize()){
        GoogleMapView(
            modifier = Modifier.matchParentSize(),
            cameraPositionState = cameraPositionState,
            onMapLoaded = {
                isMapLoaded = true
            }
        )
        if(!isMapLoaded){
            AnimatedVisibility(
                modifier = Modifier
                    .matchParentSize(),
                visible = !isMapLoaded,
                enter = EnterTransition.None,
                exit = fadeOut()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .wrapContentSize()
                )
            }
        }
    }
    Button(
        onClick = {
            navController.navigate(CocinaScreens.CocinaHomeScreen.name)
        }
    ) {
        Text(text = "Ir a la pantalla de inicio de sesión")
    }
}

@Composable
fun GoogleMapView(
    modifier: Modifier=Modifier,
    cameraPositionState: CameraPositionState = rememberCameraPositionState(),
    onMapLoaded: ()->Unit = {},
    content:@Composable () -> Unit = {}
) {
    val stateglorieta = rememberMarkerState(position = laglorieta)
    val staterest55 = rememberMarkerState(position = rest55)
    val stateajilimo = rememberMarkerState(position = ajilimo)
    val eleganciaFontFamily = FontFamily(Font(R.font.elegancia))
    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        onMapLoaded = onMapLoaded
    ){
        MarkerInfoWindowContent(
            state = stateglorieta,
            icon = BitmapDescriptorFactory.fromResource(R.drawable.olla)
        ){
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.onPrimary,
                        shape = RoundedCornerShape(35.dp,35.dp,35.dp,35.dp)
                    )
            ){
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally)
                {
                    Image(
                        painter = painterResource(id = R.drawable.glorieta),
                        contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(80.dp)
                        .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text("999 888 777",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontFamily = eleganciaFontFamily,
                            fontSize = 20.sp
                        ),
                    )
                }
            }
        }
        MarkerInfoWindowContent(
            state = staterest55,
            icon = BitmapDescriptorFactory.fromResource(R.drawable.rest)
        ){
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.onPrimary,
                        shape = RoundedCornerShape(35.dp,35.dp,35.dp,35.dp)
                    )
            ){
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally)
                {
                    Image(
                        painter = painterResource(id = R.drawable.rest55),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .height(80.dp)
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text("999 888 666",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontFamily = eleganciaFontFamily,
                            fontSize = 20.sp
                        ),
                    )
                }
            }
        }
        MarkerInfoWindowContent(
            state = stateajilimo,
            icon = BitmapDescriptorFactory.fromResource(R.drawable.aji)
        ){
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.onPrimary,
                        shape = RoundedCornerShape(35.dp,35.dp,35.dp,35.dp)
                    )
            ){
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally)
                {
                    Image(
                        painter = painterResource(id = R.drawable.ajilimo),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .height(80.dp)
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text("999 888 666",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontFamily = eleganciaFontFamily,
                            fontSize = 20.sp
                        ),
                    )
                }
            }
        }
        content()
    }
}
