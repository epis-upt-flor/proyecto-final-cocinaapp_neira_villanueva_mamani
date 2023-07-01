package com.example.cocinafeliz.navigation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    val userData = mutableStateOf<User?>(null)
}