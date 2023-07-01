package com.example.cocinafeliz.screens.login

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocinafeliz.navigation.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class LoginScreenViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)
    val currentUserId: MutableState<String?> = mutableStateOf(null)
    init {
        getCurrentUserId()
    }

    fun signInWithEmailAndPassword(email: String, password: String, home: () -> Unit) =
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("Cocina Feliz", "signInWithEmailAndPassword logueado")
                            val user = auth.currentUser
                            val userId = user?.uid
                            if (userId != null) {
                                Log.d("IDUSUARIO", "Se ha logueado ID de usuario: $userId")
                            }
                            home()
                        } else {
                            Log.d("Fallo Iniciar Sesion", "Campos Incorrectos")

                        }
                    }
            } catch (ex: Exception) {
                Log.d("Cocina Feliz", "signInWithEmailAndPassword ${ex.message}")
            }
        }

    // Creación de Usuarios
    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        nombres: String,
        apellidos: String,
        ocupacion: String,
        home: () -> Unit
    ) {
        val db = FirebaseFirestore.getInstance()
        if (_loading.value == false) {
            _loading.value = true
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        if (user != null) {
                            val userData = hashMapOf(
                                "id" to user.uid,
                                "nombres" to nombres,
                                "apellidos" to apellidos,
                                "ocupacion" to ocupacion
                            )
                            db.collection("users")
                                .document(user.uid)
                                .set(userData)
                                .addOnSuccessListener {
                                    home()
                                }
                                .addOnFailureListener { exception ->
                                    Log.d(
                                        "Cocina Feliz",
                                        "createUserWithEmailAndPassword ERROR AL GUARDAR."
                                    )
                                }
                        }
                    } else {
                        Log.d(
                            "Cocina Feliz",
                            "createUserWithEmailAndPassword: ${task.result.toString()}"
                        )
                    }
                    _loading.value = false
                }
        }
    }

    fun signOut() {
        auth.signOut()
    }
    fun getCurrentUserId() {
        val user = auth.currentUser
        currentUserId.value = user?.uid
    }
    //Tomar Usuario
    fun getUserData(userId: String): Flow<User?> = callbackFlow {
        val db = FirebaseFirestore.getInstance()
        val userDoc = db.collection("users").document(userId)
        val subscription = userDoc.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                Log.e("AVISO", "Error al obtener los datos del usuario: ${exception.message}")
                trySend(null).isSuccess // Ofrecemos un valor nulo en caso de error
            } else {
                val userData = snapshot?.toObject(User::class.java)
                trySend(userData).isSuccess // Ofrecemos los datos del usuario
                Log.e("AVISO", "Se obtuvieron el id")

            }
        }
        awaitClose { subscription.remove() } // Nos aseguramos de eliminar la suscripción al cerrar el flujo
    }

    fun updateUserData(userId: String, nombres: String, apellidos: String, ocupacion: String) {
        val db = FirebaseFirestore.getInstance()
        val userDoc = db.collection("users").document(userId)

        val userData = hashMapOf(
            "nombres" to nombres,
            "apellidos" to apellidos,
            "ocupacion" to ocupacion
            // Agrega otros campos de información del usuario aquí
        )

        userDoc.set(userData)
            .addOnSuccessListener {
                Log.d("Actualizacion de Datos", "Datos de usuario actualizados correctamente")
            }
            .addOnFailureListener { exception ->
                Log.e("Cocina Feliz", "Error al actualizar los datos de usuario: ${exception.message}")
            }
    }


}
