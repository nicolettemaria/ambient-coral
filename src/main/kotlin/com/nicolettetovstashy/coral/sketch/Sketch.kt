package com.nicolettetovstashy.coral.sketch

import com.nicolettetovstashy.coral.data.CoralDataMap
import com.nicolettetovstashy.coral.light.LightState
import com.nicolettetovstashy.coral.light.LightUtil
import com.nicolettetovstashy.coral.light.LightUtil.Companion.deriveHue
import processing.core.PApplet
import kotlin.math.roundToInt

const val light1 = 4
const val light2 = 5

var potVal: Int = 1023/2

class Sketch(
    private val lightUtil: LightUtil,
    private val coralData: CoralDataMap
) : PApplet() {
    companion object {
        fun run(lightUtil: LightUtil, coralData: CoralDataMap) {
            val art = Sketch(lightUtil, coralData)
            art.runSketch()
        }
    }

    override fun setup() {
        frame.isVisible = false

        try {
            getSerial(this)
        } catch (e: Exception) {
            e.printStackTrace()
            exit()
        }
    }

    private var time = millis()

    private var coralDataIterator = coralData.iterator()
    private var currentDataEntry = coralDataIterator.next()
    private fun nextEntry() {
        if (!coralDataIterator.hasNext()) {
            // reset to start
            coralDataIterator = coralData.iterator()
        }
        currentDataEntry = coralDataIterator.next()
    }

    private val tickIntervalMs = 500

    override fun draw() {
        getArduinoValues()
        // println("pot: $potVal")
        println("${currentDataEntry.key}: ${currentDataEntry.value.bleachingAlertArea}")

        // every tickIntervalMs, do:
        if (millis() > time + tickIntervalMs) {
            val sat = map(currentDataEntry.value.bleachingAlertArea.toFloat(),0f, 4f, 254f, 0f).roundToInt()
            // from potVal: potVal.mapToRange(0, 1023, 0, 254)

            val month = currentDataEntry.key.monthValue

            val (light1Hue, light2Hue) = when (month) {
                in 11..12, in 1..4 -> Pair(25500, 46920) // winter
                else -> Pair(deriveHue(60), deriveHue(308)) // summer
            }

            // val hue = map(((month % 12) + 8).toFloat(), 1f, 12f, 0f, 65535f).roundToInt()

            lightUtil.setLightState(light1, LightState(
                sat = sat,
                hue = light1Hue
            ))
            lightUtil.setLightState(light2, LightState(
                sat = sat,
                hue = light2Hue
            ))

            time = millis()
        }

        nextEntry()
    }
}