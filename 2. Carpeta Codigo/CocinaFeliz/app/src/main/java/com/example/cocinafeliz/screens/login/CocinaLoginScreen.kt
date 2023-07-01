package com.example.cocinafeliz.screens.login

import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cocinafeliz.navigation.CocinaScreens

@Composable
fun CocinaLoginScreen(
    navController: NavController,
    viewModel: LoginScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),

) {
    val showLoginForm = rememberSaveable { mutableStateOf(true) }
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (showLoginForm.value) {
                Text(text = "Iniciar Sesión")
                LoginUserForm(isCreateAccount = false) { email, password ->
                    Log.d("CocinaFeliz", "Logueando con $email y $password")
                    viewModel.signInWithEmailAndPassword(email, password) {
                        viewModel.getCurrentUserId() // Obtener el ID del usuario actual
                        navController.navigate(CocinaScreens.CocinaHomeScreen.name)
                    }
                }
            } else {
                Text(text = "Crea una cuenta")
                UserForm(isCreateAccount = true) { email, password, nombres, apellidos, ocupacion ->
                    Log.d("CocinaFeliz", "Creando una cuenta con $email y $password")
                    viewModel.createUserWithEmailAndPassword(
                        email,
                        password,
                        nombres,
                        apellidos,
                        ocupacion
                    ) {
                        navController.navigate(CocinaScreens.CocinaHomeScreen.name)
                    }
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val text1 = if (showLoginForm.value) "¿No tienes cuenta?" else "¿Ya tienes cuenta?"
                val text2 = if (showLoginForm.value) "Registrate" else "Inicia Sesión"
                Text(text = text1)
                Text(
                    text = text2,
                    modifier = Modifier
                        .clickable { showLoginForm.value = !showLoginForm.value }
                        .padding(start = 5.dp),
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginUserForm(
    isCreateAccount: Boolean = false,
    onDone: (String, String) -> Unit
) {
    val emailState = rememberSaveable { mutableStateOf("") }
    val passwordState = rememberSaveable { mutableStateOf("") }
    val passwordVisible = rememberSaveable { mutableStateOf(false) }
    val valido = remember(emailState.value, passwordState.value) {
        emailState.value.trim().isNotEmpty() && passwordState.value.trim().isNotEmpty()
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        EmailInput(emailState = emailState)
        PasswordInput(passwordState = passwordState, labelId = "Password", passwordVisible = passwordVisible)
        SubmitButton(textId = "Iniciar Sesión", inputValido = valido) {
            onDone(emailState.value.trim(), passwordState.value.trim())
            keyboardController?.hide()
        }
    }
}
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserForm(
    isCreateAccount: Boolean = false,
    onDone: (String, String, String, String, String) -> Unit = { email, pwd, nombres, apellidos, ocupacion -> }
) {
    val email = rememberSaveable {
        mutableStateOf("")
    }
    val password = rememberSaveable {
        mutableStateOf("")
    }
    val nombres = rememberSaveable {
        mutableStateOf("")
    }
    val apellidos = rememberSaveable {
        mutableStateOf("")
    }
    val ocupacion = rememberSaveable {
        mutableStateOf("")
    }
    val passwordVisible = rememberSaveable {
        mutableStateOf(false)
    }
    val valido = remember(
        email.value,
        password.value,
        nombres.value,
        apellidos.value,
        ocupacion.value
    ) {
        email.value.trim().isNotEmpty() &&
                password.value.trim().isNotEmpty() &&
                nombres.value.trim().isNotEmpty() &&
                apellidos.value.trim().isNotEmpty() &&
                ocupacion.value.trim().isNotEmpty()
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        EmailInput(emailState = email)
        PasswordInput(passwordState = password, labelId = "Contraseña", passwordVisible = passwordVisible)
        InputField(valueState = nombres, labelId = "Nombres", keyboardType = KeyboardType.Text)
        InputField(valueState = apellidos, labelId = "Apellidos", keyboardType = KeyboardType.Text)
        InputField(valueState = ocupacion, labelId = "Ocupación", keyboardType = KeyboardType.Text)
        SubmitButton(
            textId = if (isCreateAccount) "Crear cuenta" else "Login",
            inputValido = valido
        ) {
            onDone(
                email.value.trim(),
                password.value.trim(),
                nombres.value.trim(),
                apellidos.value.trim(),
                ocupacion.value.trim()
            )
            keyboardController?.hide()
        }
    }
}

@Composable
fun SubmitButton(
    textId: String,
    inputValido: Boolean,
    onClic : () -> Unit
){
    Button(onClick = onClic,
    modifier = Modifier
        .padding(3.dp)
        .fillMaxWidth(),
        shape = CircleShape,
        enabled = inputValido
    )
    {
        Text(text = textId,
            modifier = Modifier
                .padding(5.dp))
        
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordInput(
    passwordState: MutableState<String>,
    labelId: String,
    passwordVisible: MutableState<Boolean>)
{
    val visualTransformation = if(passwordVisible.value)
        VisualTransformation.None
    else PasswordVisualTransformation()
    OutlinedTextField(
        value = passwordState.value,
        onValueChange = { newValue -> passwordState.value = newValue },
        label = { Text(text = labelId) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        modifier = Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        visualTransformation = visualTransformation,
        trailingIcon = {
            if(passwordState.value.isNotBlank()){
                PasswordVisibleIcon(passwordVisible)
            }
            else null
        }

    )
}

@Composable
fun PasswordVisibleIcon(
    passwordVisible: MutableState<Boolean>
)
{
    val image =
        if (passwordVisible.value)
            Icons.Default.VisibilityOff
    else
        Icons.Default.Visibility
    IconButton(onClick = {
        passwordVisible.value = !passwordVisible.value
    }) {
        Icon(
            imageVector = image,
            contentDescription = ""
        )

        
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    valueState: MutableState<String>,
    labelId: String,
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType
)
{
    OutlinedTextField(
        value = valueState.value,
        onValueChange = { newValue -> valueState.value = newValue },
        label = { Text(text = labelId) },
        singleLine = isSingleLine,
        modifier = Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        )
    )
}

@Composable
fun EmailInput(
    emailState: MutableState<String>,
    labelId: String = "Email")
{
    InputField(
        valueState = emailState,
        labelId = labelId,
        keyboardType = KeyboardType.Email
    )

}
