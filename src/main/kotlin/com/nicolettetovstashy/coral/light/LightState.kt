package com.nicolettetovstashy.coral.light

data class LightState(
    val on: Boolean? = true,
    val sat: Int? = 254,
    val bri: Int? = 254,
    val xy: Pair<Double, Double>? = null,
    val hue: Int? = null
) {
    fun toMap(): Map<*, *> {
        return mapOf(
            "on" to on,
            "sat" to sat,
            "bri" to bri,
            "xy" to xy?.let { listOf(xy.first, xy.second) },
            "hue" to hue
        )
    }
}