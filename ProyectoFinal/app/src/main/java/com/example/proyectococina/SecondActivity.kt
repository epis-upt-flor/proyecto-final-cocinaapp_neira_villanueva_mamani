package com.example.proyectococina

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SecondActivity(navController: NavController, ingredients: List<String>) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Segunda actividad") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver atrás")
                    }
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.White)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Text("Lista de ingredientes:", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                Column {
                    ingredients.forEachIndexed { index, ingredient ->
                        Text("${index + 1}. $ingredient", fontSize = 16.sp)
                        Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))
                    }
                }
            }
        }
    )
}

