package com.example.cocinafeliz.screens.chatgpt

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ChatGptViewModel : ViewModel() {
    //val chatGpt = ChatGPT()
    val conversation = mutableStateListOf<String>()

    init {
        viewModelScope.launch {
           // chatGpt.initialize()
        }
    }

    fun sendMessage(message: String) {
        conversation.add("User: $message")
        //val response = chatGpt.sendMessage(message)
        //conversation.add("ChatGPT: $response")
    }
}
