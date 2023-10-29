package com.niranjan.chatgpt.data.remote

import com.niranjan.chatgpt.BuildConfig
import com.niranjan.chatgpt.data.remote.dto.ChatGPTDto
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ChatGPTApi {
    @POST(value = "chat/completions")
    @Headers("Authorization: Bearer ${BuildConfig.API_KEY}")
    suspend fun chatCompletions(@Body chatCompletionRequestModel: ChatCompletionRequestModel): ChatGPTDto

    companion object {
        const val BASE_URL = "https://api.openai.com/v1/"
    }
}