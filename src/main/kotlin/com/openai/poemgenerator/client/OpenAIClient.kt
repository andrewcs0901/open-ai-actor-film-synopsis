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
    @Value("\${service.open-ia.key}")
    val openAiKey: String? = null
) {

    private val modelId: String = "gpt-3.5-turbo"
    private val systemPrompt: String =
        "I want you to act as a poet. You will create poems that evoke emotions and have the power to stir people’s soul. Write on any topic or theme but make sure your words convey the feeling you are trying to express in beautiful yet meaningful ways. You can also come up with short verses that are still powerful enough to leave an imprint in readers' minds. You must include a html tag <br> on each verse and <p> on stanzas"

    private var openAIService = OpenAI(
        token = openAiKey ?: throw AuthenticationException("OpenAI key not found")
    )

    @OptIn(BetaOpenAI::class)
    suspend fun createPoem(title: String): String {
        val chatCompletionRequest = ChatCompletionRequest(
            model = ModelId(modelId), messages = listOf(
                ChatMessage(role = ChatRole.System, content = systemPrompt),
                ChatMessage(role = ChatRole.User, content = "My first request is a poem about the title \"$title\"")
            )
        )
        return openAIService.chatCompletion(chatCompletionRequest).choices.first().message?.content ?: "No message"
    }
}