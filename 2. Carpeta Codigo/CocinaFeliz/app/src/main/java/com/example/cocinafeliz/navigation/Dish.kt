package com.example.cocinafeliz.navigation

//Esta clase sera unicamente usada para el carrusel de imagenes:
data class Dish(
    val id: Int,
    val name: String,
    val description: String,
    val ingredients: List<String>,
    val preparation: List<String>,
    val imageUrl: Int
)