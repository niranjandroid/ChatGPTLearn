package com.niranjan.chatgpt.ui.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niranjan.chatgpt.data.remote.ChatCompletionRequestModel
import com.niranjan.chatgpt.data.remote.ChatGPTApi
import com.niranjan.chatgpt.data.remote.Message
import com.niranjan.chatgpt.data.remote.dto.Choice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class ChatViewModel(val chatGptApi: ChatGPTApi) : ViewModel() {

    var messages = MutableStateFlow<List<Message>>(emptyList())
        private set

    fun requestGpt(message: String) {
        val messageObj = Message(message, "user") // mapper
        viewModelScope.launch(Dispatchers.IO) {// explicitly mentioning IO infuture move the logic to repo and implememnt room
            messages.emit(messages.value.toMutableList().apply { add(messageObj) })
            try {
                val response = chatGptApi.chatCompletions(
                    ChatCompletionRequestModel(messages = messages.value)
                ) // mapper
                messages.emit(messages.value.toMutableList().apply { addAll(response.choices.map { it.message }.toMutableList()) })
            } catch (ex: Exception) {
                Log.d("TEST@!", ex.stackTraceToString())
            }
        }
    }
}