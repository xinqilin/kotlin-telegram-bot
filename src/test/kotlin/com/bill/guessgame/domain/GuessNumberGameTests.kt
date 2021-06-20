package com.bill.guessgame.domain

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class GuessNumberGameTests {

    @Test
    internal  fun test(){
        //arrange
        val guessNumberGame = GuessNumberGame()

        //act
        val result = guessNumberGame.check("")

        //assert
        Assertions.assertThat(result).isEqualTo("")
    }
}