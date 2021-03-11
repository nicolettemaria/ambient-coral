package com.nicolettetovstashy.coral

import com.fasterxml.jackson.databind.JsonNode
import com.nicolettetovstashy.coral.sketch.Sketch
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking

const val bridgeIp = "192.168.1.4"
const val username = "AIgdIkyTOmYXbkxLKJSRiVWz8Nl15flPoiyNDDO0"

fun main(args: Array<String>) {
    val client = HttpClient() {
        install(JsonFeature) {
            serializer = JacksonSerializer()
        }
    }

    runBlocking {
        val lights = client.get<JsonNode>("http://$bridgeIp/api/$username/lights")
        println(lights.toPrettyString())

        val currentLight = 4

        client.put<String>("http://$bridgeIp/api/$username/lights/$currentLight/state") {
            contentType(ContentType.Application.Json)
            body = mapOf(
                "on" to true,
                "sat" to 254,
                "bri" to 254,
                "xy" to listOf(0.675,0.8)
            )
        }
    }

    // Sketch.run()
}