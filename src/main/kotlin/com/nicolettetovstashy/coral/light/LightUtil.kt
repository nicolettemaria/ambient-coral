package com.nicolettetovstashy.coral.light

import com.fasterxml.jackson.databind.JsonNode
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.*
import processing.core.PApplet
import kotlin.math.floor
import kotlin.math.roundToInt

class LightUtil(
    private val bridgeIp: String = "192.168.1.4",
    private val username: String = "AIgdIkyTOmYXbkxLKJSRiVWz8Nl15flPoiyNDDO0"
) {
    private val client = HttpClient() {
        install(JsonFeature) {
            serializer = JacksonSerializer()
        }
    }

    private val previousLightStates: MutableMap<Int, LightState> = mutableMapOf()

//    init {
//        runBlocking {
//            val lights = client.get<JsonNode>("http://$bridgeIp/api/$username/lights")
//            println(lights.toPrettyString())
//        }
//    }

    fun setLightState(light: Int, state: LightState) {
        if (previousLightStates[light] != state) {
            GlobalScope.launch {
                client.put<String>("http://$bridgeIp/api/$username/lights/$light/state") {
                    contentType(ContentType.Application.Json)
                    body = state.toMap()
                }
            }

            previousLightStates[light] = state
        }
    }

    companion object {
        fun deriveHue(hVal: Int): Int = floor((65535 * hVal / 360).toFloat()).roundToInt()
    }
}