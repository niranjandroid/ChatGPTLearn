package com.niranjan.chatgpt.data.remote.dto

import com.niranjan.chatgpt.data.remote.Message
import com.squareup.moshi.Json

data class ChatGPTDto(
    val choices: List<Choice>,
    val created: Long,
    val id: String,
    val model: String,
    val usage: Usage,
)

data class Choice(
    @field:Json(name = "finish_reason")
    val finishReason: String,
    val index: Long,
    val message: Message,
)

data class Usage(
    @field:Json(name = "completion_tokens")
    val completionTokens: Long,
    @field:Json(name = "prompt_tokens")
    val promptTokens: Long,
    @field:Json(name = "total_tokens")
    val totalTokens: Long,
)