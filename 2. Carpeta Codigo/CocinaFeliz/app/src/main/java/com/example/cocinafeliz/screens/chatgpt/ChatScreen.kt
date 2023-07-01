package com.example.cocinafeliz.screens.chatgpt

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ChatScreen(
    viewModel: ChatGptViewModel = viewModel()
) {
    val conversation = viewModel.conversation
    val questionState = remember { mutableStateOf(TextFieldValue()) }

    Column {
        LazyColumn {
            items(conversation) { message ->
                Text(text = message)
            }
        }

        TextField(
            value = questionState.value,
            onValueChange = { questionState.value = it },
            label = { Text("Ingrese su pregunta o respuesta") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val question = questionState.value.text
                if (question.isNotBlank()) {
                    viewModel.sendMessage(question)
                    questionState.value = TextFieldValue()
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Enviar")
        }
    }
}
