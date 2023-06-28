package com.openai.synopsisgenerator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SynopsisGeneratorApplication

fun main(args: Array<String>) {
	runApplication<SynopsisGeneratorApplication>(*args)
}