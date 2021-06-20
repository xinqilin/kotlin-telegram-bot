package com.bill.guessgame.domain

import org.slf4j.LoggerFactory
import kotlin.random.Random

class GuessNumberGame {

    private val logger = LoggerFactory.getLogger(this::class.java)

//    private val password = Random(System.currentTimeMillis()).nextInt(1000,10000)

    private var password = initPassword()

    fun check(input: String) = password

    private fun initPassword() =
            (0..9)
                    .toList()
                    .shuffled()
                    .take(4)
                    .joinToString { "" }
                    .also {
                        logger.info(it)
                    }
}