package com.nicolettetovstashy.coral

import com.nicolettetovstashy.coral.sketch.Sketch
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking

const val bridgeIp = "192.168.1.4"
const val username = "AIgdIkyTOmYXbkxLKJSRiVWz8Nl15flPoiyNDDO0"

fun main(args: Array<String>) {
    val client = HttpClient()

    runBlocking {
        val lights = client.get<String>("http://$bridgeIp/api/$username/lights")
        println(lights)
    }

    // Sketch.run()
}