package com.bill.guessgame

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class GuessgameApplication

fun main(args: Array<String>) {
    runApplication<GuessgameApplication>(*args)
}
