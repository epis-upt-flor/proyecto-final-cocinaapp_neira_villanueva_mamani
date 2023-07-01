package com.example.cocinafeliz.screens.home

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cocinafeliz.R
import com.example.cocinafeliz.screens.login.LoginScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DatosUser(
    userId: String,
    viewModel: LoginScreenViewModel,
    navController: NavController
) {
    val userData by viewModel.getUserData(userId).collectAsState(initial = null)
    val eleganciaFontFamily = FontFamily(Font(R.font.elegancia))
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        // Manejar la URI de la imagen seleccionada aquí
        if (uri != null) {
            // Asignar la URI a la variable de estado imageUri
            imageUri = uri
        }
    }
    LaunchedEffect(userId) {
        viewModel.getUserData(userId)
    }

    userData?.let { user ->
        var nombres by remember { mutableStateOf(user.nombres) }
        var apellidos by remember { mutableStateOf(user.apellidos) }
        var ocupacion by remember { mutableStateOf(user.ocupacion) }
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
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                )
            },
            content = {
                Surface(modifier = Modifier.padding(16.dp), color = Color.White) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 32.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Campos de texto editables
                        TextField(
                            value = nombres,
                            onValueChange = { nombres = it },
                            label = { Text("Nombre") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        TextField(
                            value = apellidos,
                            onValueChange = { apellidos = it },
                            label = { Text("Apellidos") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        TextField(
                            value = ocupacion,
                            onValueChange = { ocupacion = it },
                            label = { Text("Ocupación") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        // ...otros campos editables

                        Button(
                            onClick = { viewModel.updateUserData(userId, nombres, apellidos, ocupacion)
                                navController.popBackStack() },
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text("Guardar")
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        Button(onClick = { launcher.launch("image/*") }) {
                            Text("Seleccionar imagen")
                        }
                    }
                }
            },
        )
    }
}



