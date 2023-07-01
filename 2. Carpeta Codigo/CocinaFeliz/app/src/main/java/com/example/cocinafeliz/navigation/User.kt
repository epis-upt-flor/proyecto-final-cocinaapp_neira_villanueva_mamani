package com.example.cocinafeliz.navigation

data class User(
    val id: String = "",
    val nombres: String = "",
    val apellidos: String = "",
    val ocupacion: String = "",
    val profileImageUrl: String = "" // Agrega el campo para la URL de la imagen de perfil
) {
    // Constructor sin argumentos
    constructor() : this("", "", "", "", "")
}