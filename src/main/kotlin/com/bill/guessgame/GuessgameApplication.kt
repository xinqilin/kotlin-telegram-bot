package com.bill.guessgame

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GuessgameApplication

fun main(args: Array<String>) {
    runApplication<GuessgameApplication>(*args)
}
