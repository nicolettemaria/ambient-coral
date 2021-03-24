package com.nicolettetovstashy.coral.sketch

import com.nicolettetovstashy.coral.data.DateCoralData
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
    private val coralData: List<DateCoralData>
) : PApplet() {
    companion object {
        fun run(lightUtil: LightUtil, coralData: List<DateCoralData>) {
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

    private var coralDataIndex = 0
    private fun nextEntry() {
        if (coralDataIndex == coralData.size - 1) {
            // reset to start
            coralDataIndex = 0
        }
        coralDataIndex++
    }

    private val tickIntervalMs = 500

    private var lastRawPotVals = mutableListOf<Int>()
    private var lastPotVal = 0

    override fun draw() {
        getArduinoValues()
        // println("pot: $potVal")
        lastRawPotVals.add(potVal)
        lastRawPotVals = lastRawPotVals.takeLast(10).toMutableList()
        val resolvedPotVal = lastRawPotVals.average().roundToInt()
        // println("pot: $resolvedPotVal")

        if (resolvedPotVal >= lastPotVal + 5 || resolvedPotVal <= lastPotVal - 5) {
            lastPotVal = resolvedPotVal

            // move to new time
            val newDataIndex = floor(map(resolvedPotVal.toFloat(), 100f, 1023f, 0f, (coralData.size - 1).toFloat())).coerceIn(coralData.indices)
            coralDataIndex = newDataIndex
        }

        val currentDataEntry = coralData[coralDataIndex]

        // println("${currentDataEntry.date}: ${currentDataEntry.bleachingAlertArea}")

        // send month/baa over to arduino
        if (currentDataEntry.date.dayOfMonth == 1) {
            sendOverSerial("${currentDataEntry.date.monthValue.toString().padStart(2, '0')}/${currentDataEntry.date.year}" + "\n")

            var monthlyBaaMax = 0
            for (dataI in coralDataIndex until coralData.size) {
                val entry = coralData[dataI]
                if (entry.date.monthValue != currentDataEntry.date.monthValue) break
                monthlyBaaMax = max(monthlyBaaMax, entry.bleachingAlertArea)
            }

            sendOverSerial(monthlyBaaMax.toString() + "\n")
        }

        // every tickIntervalMs (500ms), update lights
        if (millis() > time + tickIntervalMs) {
            val sat = map(currentDataEntry.bleachingAlertArea.toFloat(),0f, 4f, 254f, 0f).roundToInt()
            // from potVal: potVal.mapToRange(0, 1023, 0, 254)

            val month = currentDataEntry.date.monthValue

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