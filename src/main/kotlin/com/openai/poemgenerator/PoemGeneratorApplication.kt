package com.openai.poemgenerator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PoemGeneratorApplication

fun main(args: Array<String>) {
	runApplication<PoemGeneratorApplication>(*args)
}