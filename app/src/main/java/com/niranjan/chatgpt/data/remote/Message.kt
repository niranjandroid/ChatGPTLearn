package com.niranjan.chatgpt.data.remote

import com.squareup.moshi.Json

data class Message(
    val content: String,
    val role: String,
) {
    @Transient
    val isUser = role == "user"
}