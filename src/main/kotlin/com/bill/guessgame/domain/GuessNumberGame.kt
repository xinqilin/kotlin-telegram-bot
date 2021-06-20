package com.bill.guessgame.domain

import org.slf4j.LoggerFactory
import java.util.*
import kotlin.random.Random

class GuessNumberGame {

    private val logger = LoggerFactory.getLogger(this::class.java)

//    private val password = Random(System.currentTimeMillis()).nextInt(1000,10000)

    var password = initPassword()

    fun check(input: String) =
            run {
                var a = 0
                var b = 0
                var map: TreeMap<Char, Int> = TreeMap()
                for ((index, char) in password.toCharArray().withIndex()) {
                    map[char] = index
                }

                for ((index, guessNumber) in input.toCharArray().withIndex()) {
                    when {
                        map.containsKey(guessNumber) && map[guessNumber] == index -> a++
                        map.containsKey(guessNumber) && map[guessNumber] != index -> b++
                    }
                }

                "${a}A${b}B"
            }

    private fun initPassword() =
            (0..9)
                    .toList()
                    .shuffled()
                    .take(4)
                    .joinToString { "" }
                    .also {
                        logger.info("init password: $it")
                    }

    public fun again() =
            run {
                password = this.initPassword()
                logger.info("new Game")
                "new Game"
            }
}