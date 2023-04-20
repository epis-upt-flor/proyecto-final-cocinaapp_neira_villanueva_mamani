package com.example.proyectococina

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ComidasActivity(navController: NavController) {
    val platillos = listOf(
        Platillo(
            R.drawable.imagen1,
            "Chicken Al Pastor",
            listOf("6 large cloves garlic, peeled", "⅔ cup orange juice", "3 tablespoons olive oil")
        ),
        Platillo(
            R.drawable.imagen2,
            "Mississippi Chicken",
            listOf("1 cup sliced and drained pepperoncini peppers", "4 tablespoons unsalted butter, sliced", "1/2 cup water")
        ),
        Platillo(
            R.drawable.imagen3,
            "Lasagna Flatbread",
            listOf("6 flatbreads", "1 pound sausage", "1 egg")
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Comidas") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver atrás")
                    }
                }
            )
        },
        content = {
            LazyColumn {
                items(platillos) { platillo ->
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        elevation = 4.dp,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .clickable { /* Acción al hacer click en el platillo */ }
                    ) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Image(
                                painter = painterResource(id = platillo.imagen),
                                contentDescription = null,
                                modifier = Modifier
                                    .height(180.dp)
                                    .fillMaxWidth()
                                    .clip(shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                            )
                            Column(
                                modifier = Modifier
                                    .background(Color.White)
                                    .padding(16.dp)
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = platillo.titulo,
                                    style = MaterialTheme.typography.h4,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                platillo.ingredientes.forEach { ingrediente ->
                                    Text(
                                        text = ingrediente,
                                        style = MaterialTheme.typography.body1,
                                        modifier = Modifier.padding(bottom = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

data class Platillo(val imagen: Int, val titulo: String, val ingredientes: List<String>)
