package com.example.proyectococina

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ingredients = mutableListOf<String>()

        setContent {
            val navController = rememberNavController()
            NavHost(navController, startDestination = "splash_screen") {
                composable("splash_screen") {
                    val handler = Handler(Looper.getMainLooper())
                    handler.postDelayed({
                        navController.popBackStack()
                        navController.navigate("main_activity") {
                            popUpTo("splash_screen") { inclusive = true }
                        }
                    }, 2500)
                    SplashScreen(navController = navController)
                }
                composable("main_activity") {
                    App(navController, ingredients)
                }
                composable("second_activity") {
                    SecondActivity(navController, ingredients)
                }
                composable("login") {
                    LoginActivity(navController)
                }
                composable("comidas") {
                    ComidasActivity(navController)
                }
            }
        }
    }
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun App(navController: NavHostController, ingredients: MutableList<String>) {
    val ingredients = remember { mutableStateListOf<String>() }
    val amounts = remember { mutableStateListOf<Float>() }
    val measures = remember { mutableStateListOf<String>() }
    var ingredientText by remember { mutableStateOf("") }
    var amountText by remember { mutableStateOf("") }
    var measureText by remember { mutableStateOf("") }
    val measureTextFocus = remember { FocusRequester() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista:") },
                actions = {
                    IconButton(onClick = { navController.navigate("login") }) {
                        Icon(Icons.Filled.AccountCircle, contentDescription = "Iniciar sesión")
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
            ) {
                TextField(
                    value = ingredientText,
                    onValueChange = { ingredientText = it },
                    label = { Text("Ingrediente") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    TextField(
                        value = amountText,
                        onValueChange = { amountText = it },
                        label = { Text("Cantidad") },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { measureTextFocus.requestFocus() }
                        ),
                    )
                    MeasureSelector(
                        measureText = measureText,
                        onMeasureSelected = { measureText = it },
                    )
                    IconButton(
                        onClick = {
                            if (ingredientText.isNotBlank() && amountText.isNotBlank() && measureText.isNotBlank()) {
                                ingredients.add(ingredientText)
                                amounts.add(amountText.toFloat())
                                measures.add(measureText)
                                ingredientText = ""
                                amountText = ""
                                measureText = ""
                            }
                        }
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = "Agregar")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .verticalScroll(rememberScrollState())
                ) {
                    ingredients.forEachIndexed { index, ingredient ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 8.dp)
                        ) {
                            Text(
                                "$ingredient ${amounts[index]} ${measures[index]}",
                                fontSize = 18.sp,
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(
                                onClick = {
                                    ingredients.removeAt(index)
                                    amounts.removeAt(index)
                                    measures.removeAt(index)
                                }
                            ) {
                                Icon(Icons.Filled.Delete, contentDescription = "Eliminar")
                            }
                        }
                        Divider(color = Color.Black)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { navController.navigate("comidas") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    Text("Ver comidas")
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("second_activity") }) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar")
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        isFloatingActionButtonDocked = true,
    )
}



@Composable
fun MeasureSelector(
    measureText: String,
    onMeasureSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val measures = listOf("gr", "kl", "unit")

    Box(modifier = Modifier.wrapContentSize()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextButton(
                onClick = { expanded = true },
                modifier = Modifier.padding(4.dp)
            ) {
                Text(if (measureText.isBlank()) "Med." else measureText)
            }
            IconButton(
                onClick = { expanded = true }
            ) {
                Icon(Icons.Filled.ArrowDropDown, contentDescription = "Desplegar")
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            measures.forEach { medida ->
                DropdownMenuItem(onClick = {
                    onMeasureSelected(medida)
                    expanded = false
                }) {
                    Text(text = medida)
                }
            }
        }
    }
}




