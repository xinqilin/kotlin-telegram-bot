package com.bill.guessgame.schedule

import com.bill.guessgame.domain.GuessNumberGame
import com.fasterxml.jackson.databind.JsonNode
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@Component
class PullMessageSchedule {

    private val logger = LoggerFactory.getLogger(this::class.java)

    private val webClient = WebClient.create("https://api.telegram.org/bot1849831221:AAFpkmHsvoDvSIOdAQod35AsN2lxq6p-mdU")

    private var offset = 0L

    @Scheduled(cron = "*/5 * * * * *")
    fun fetch() =
            runBlocking {
                val message = webClient.get()
                        .uri {
                            it.path("/getUpdates")
                                    .queryParam("/offset").build()
                        }
                        .retrieve()
                        .awaitBody<JsonNode>()

                if (message.get("result").toList().isNotEmpty()) {
                    offset = message.get("result").toList().last().get("update_id").longValue() + 1

                    message.get("result").toList().forEach{
                        val input = it.get("message").get("text").asText()

                        logger.info(GuessNumberGame().check(input))
                    }
                }
            }


}