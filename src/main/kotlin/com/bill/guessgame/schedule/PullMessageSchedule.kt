package com.bill.guessgame.schedule

import com.bill.guessgame.domain.GuessNumberGame
import com.fasterxml.jackson.databind.JsonNode
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@Component
class PullMessageSchedule {

    private val token = ""

    private val logger = LoggerFactory.getLogger(this::class.java)

    private val webClient = WebClient.create("https://api.telegram.org/bot$token")

    private val guessNumberGame = GuessNumberGame()

    private var offset = 0L

    @Scheduled(cron = "*/3 * * * * *")
    fun invoke() = runBlocking {
        val messages = fetch()
        val result = messages.get("result").toList()

        if (result.isNotEmpty()) {
            offset = result.last().get("update_id").longValue() + 1

            result.map {
                val input = it.get("message").get("text").asText()
                val chatId = it.get("message").get("from").get("id").longValue()
                when {
                    "again".equals(input) -> Pair(chatId, guessNumberGame.again())

                    4 != input.toHashSet().size -> Pair(chatId, "please input 4 distinct numbers")

                    "[0-9]{4}".toRegex().matches(input) -> Pair(chatId, guessNumberGame.check(input))

                    else -> Pair(chatId, "unsupported message")
                }
            }.forEach {
                send(it.first, it.second)
            }
        }
    }

    private suspend fun fetch() =
            webClient.get()
                    .uri { builder ->
                        builder.path("/getUpdates")
                                .queryParam("offset", offset)
                        builder.build()
                    }
                    .retrieve()
                    .awaitBody<JsonNode>()

    private suspend fun send(chatId: Long, text: String) =
            webClient.post()
                    .uri("/sendMessage")
                    .header("Content-Type", "application/json")
                    .body(
                            BodyInserters.fromValue(
                                    mapOf("chat_id" to chatId, "text" to text)
                            )
                    )
                    .retrieve()
                    .awaitBody<JsonNode>()

    //=== first version ===
//    @Scheduled(cron = "*/5 * * * * *")
//    fun fetch() =
//            runBlocking {
//                val message = webClient.get()
//                        .uri {
//                            it.path("/getUpdates")
//                                    .queryParam("/offset").build()
//                        }
//                        .retrieve()
//                        .awaitBody<JsonNode>()
//
//                if (message.get("result").toList().isNotEmpty()) {
//                    offset = message.get("result").toList().last().get("update_id").longValue() + 1
//
//                    message.get("result").toList().forEach{
//                        val input = it.get("message").get("text").asText()
//
//                        val chat_id = it.get("message").get("from").get("id").longValue()
//                        val text = guessNumberGame.check(input)
//
//                        logger.info(GuessNumberGame().check(input))
//
//                        webClient.post()
//                                .uri("/sendMessage")
//                                .header("Content-Type","application/json")
//                                .body(BodyInserters.fromValue(mapOf("chat_id" to chat_id, "text" to text)))
//                                .retrieve()
//                                .awaitBody<JsonNode>()
//                    }
//                }
//            }


}