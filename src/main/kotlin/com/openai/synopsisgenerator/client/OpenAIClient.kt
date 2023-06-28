package com.openai.synopsisgenerator.client

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.exception.AuthenticationException
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus.*
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class OpenAIClient(
    @Value("\${service.open-ai.key}")
    val openAiKey: String? = null
) {

    private val modelId: String = "gpt-3.5-turbo"
    private val systemPrompt: String =
        """
            I want you to act as a scriptwriter. 
            You will create synopsis that evoke emotions and have the power to stir people’s soul. 
            Write on any topic or theme but make sure your words convey the feeling you are trying to express in beautiful yet meaningful ways. 
            You can also come up with short verses that are still powerful enough to leave an imprint in readers' minds. 
        """
    private lateinit var openAIService: OpenAI

    init {
        openAiKey?.let {
            openAIService = OpenAI(token = it)
        } ?: throw ResponseStatusException(UNAUTHORIZED, "User Unauthorized - Open AI Key Not Set")
    }

    @OptIn(BetaOpenAI::class)
    suspend fun createSynopsis(actor: String,genre: String): String {
        val chatCompletionRequest = ChatCompletionRequest(
            model = ModelId(modelId), messages = listOf(
                ChatMessage(role = ChatRole.System, content = systemPrompt),
                ChatMessage(role = ChatRole.User, content = "my first request is a movie synopsis with Actor \"$actor\" and the \"$genre\" genre ")
            )
        )

        try {
            val response = openAIService.chatCompletion(chatCompletionRequest)
            return response.choices.first().message?.content ?: throw ResponseStatusException(NOT_FOUND, "No message")
        } catch (e: AuthenticationException) {
            throw ResponseStatusException(UNAUTHORIZED, "User Unauthorized - Invalid Open AI key, $e")
        }
    }
}