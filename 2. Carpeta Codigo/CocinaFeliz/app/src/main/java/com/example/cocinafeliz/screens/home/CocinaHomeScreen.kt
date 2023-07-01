package com.example.cocinafeliz.screens.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DrawerValue
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.ModalDrawer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.cocinafeliz.R
import com.example.cocinafeliz.constants.Constants.ad_id_banner
import com.example.cocinafeliz.maps.MapasScreen
import com.example.cocinafeliz.navigation.CocinaScreens
import com.example.cocinafeliz.navigation.Dish
import com.example.cocinafeliz.navigation.User
import com.example.cocinafeliz.screens.login.LoginScreenViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CocinaHomeScreen(
    navController: NavController,
    viewModel: LoginScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val eleganciaFontFamily = FontFamily(Font(R.font.elegancia))
    val isLoggedIn = rememberSaveable { mutableStateOf(true) }
    //Id del usuario que entra:
    val currentUserId: String? by viewModel.currentUserId

    ModalDrawer(
        drawerState = drawerState,
        drawerContent = {
            Button(
                onClick = {
                    navController.navigate(CocinaScreens.MapasScreen.name)
                },
                shape = RoundedCornerShape(0.dp), // Esquinas rectas
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Restauranes")
            }
            Button(
                onClick = {
                    val userId = currentUserId // Reemplaza esto con el ID real del usuario
                    navController.navigate("${CocinaScreens.DatosUser.name}/$userId")
                    Log.d("ID EN HOME", "Este es el ID: $userId")

                },
                shape = RoundedCornerShape(0.dp), // Esquinas rectas
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Editar Usuario")
            }
            Button(
                onClick = {
                    navController.navigate(CocinaScreens.Soporte.name)
                },
                shape = RoundedCornerShape(0.dp), // Esquinas rectas
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Soporte y Ayuda")
            }
            Button(
                onClick = {
                    // Acción para el botón "Listas"
                },
                shape = RoundedCornerShape(0.dp), // Esquinas rectas
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Ingredientes")
            }
            Button(
                onClick = {
                    navController.navigate(CocinaScreens.Notificacion.name)
                },
                shape = RoundedCornerShape(0.dp), // Esquinas rectas
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Alarmas")
            }
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)) {
                AnuncioSuperior()

            }
        },
        content = {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text("YUMMLY  𓁉",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontFamily = eleganciaFontFamily,
                                    fontSize = 40.sp
                                ),
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(
                                    imageVector = Icons.Filled.Menu,
                                    contentDescription = "Menu"
                                )
                            }
                        },
                        actions = {
                            IconButton(onClick = {
                                if (isLoggedIn.value) {
                                    viewModel.signOut()
                                    isLoggedIn.value = false
                                    navController.navigate(CocinaScreens.LoginScreen.name)
                                } else {
                                    // Acción para iniciar sesión
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Person,
                                    contentDescription = "User"
                                )
                            }
                        }
                    )
                },
                content = {
                    Surface(modifier = Modifier.padding(16.dp), color = Color.Black) {
                        if (isLoggedIn.value) {
                            Text(text = "Estamos en Home!!")
                        } else {
                            Text(text = "Has cerrado sesión")
                        }
                    }
                },


            )
        }
    )
}


@Composable
fun AnuncioSuperior() {
    val adWidht = LocalConfiguration.current.screenWidthDp - 32
    AndroidView(
        factory ={context->
            val adView = AdView(context)
            adView.setAdSize(AdSize(adWidht,50))
            adView.apply {
                adUnitId = ad_id_banner
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}
