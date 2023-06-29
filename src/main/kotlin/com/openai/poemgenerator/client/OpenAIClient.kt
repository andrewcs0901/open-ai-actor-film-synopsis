package com.openai.poemgenerator.client

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import javax.naming.AuthenticationException


@Service
class OpenAIClient(
    @Value("\${service.open-ai.key}")
    val openAiKey: String? = null
) {

    private val modelId: String = "gpt-3.5-turbo"
     private val systemPrompt: String =
        "Você é um escritor de contos"

    private var openAIService = OpenAI(
        token = openAiKey ?: throw AuthenticationException("OpenAI key not found")
    )

    @OptIn(BetaOpenAI::class)
    suspend fun createTale(title: String): String {
        val chatCompletionRequest = ChatCompletionRequest(
            model = ModelId(modelId), messages = listOf(
                ChatMessage(role = ChatRole.System, content = systemPrompt),
                ChatMessage(role = ChatRole.User, content = "Escreva-me um conto com o seguinte título: \"$title\"")
            )
        )
        return openAIService.chatCompletion(chatCompletionRequest).choices.first().message?.content ?: "No message"
    }
}