package com.openai.talegenerator.controller

import com.openai.talegenerator.client.OpenAIClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/open-ai")
class TaleGeneratorController(private val serviceClient: OpenAIClient) {

    @GetMapping("/tale")
    suspend fun taleGenerator(@RequestParam("title") title: String): String {
        return serviceClient.createTale(title)
    }
}