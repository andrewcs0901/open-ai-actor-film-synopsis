package com.andrew.poemgenerator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class PoemGeneratorApplication

fun main(args: Array<String>) {
	runApplication<PoemGeneratorApplication>(*args)
}

@RestController
class MessageController {
	val service = OpenAIClient("YOUR-API-KEY")

	@GetMapping("/poem")
	suspend fun poemGenerator(@RequestParam("title") title: String): String {
		return service.createPoem(title)
	}
}