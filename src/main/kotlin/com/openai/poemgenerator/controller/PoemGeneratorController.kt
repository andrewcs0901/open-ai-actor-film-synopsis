package com.openai.poemgenerator.controller

import com.openai.poemgenerator.client.OpenAIClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/open-ai")
class PoemGeneratorController(private val serviceClient: OpenAIClient) {

    @GetMapping("/poem")
    suspend fun poemGenerator(@RequestParam("title") title: String): String {
        return serviceClient.createPoem(title)
    }
}