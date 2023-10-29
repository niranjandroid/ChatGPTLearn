package com.niranjan.chatgpt.data.remote

data class ChatCompletionRequestModel(
    val model: String = "gpt-3.5-turbo",
    val messages: List<Message> = emptyList()
)
