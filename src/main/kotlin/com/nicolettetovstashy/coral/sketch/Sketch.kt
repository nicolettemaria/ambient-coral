package com.nicolettetovstashy.coral.sketch

import processing.core.PApplet

var potVal: Int? = null

class Sketch : PApplet() {
    companion object Factory {
        fun run() {
            val art = Sketch()
            art.runSketch()
        }
    }

    override fun setup() {
        setSize(500, 500)

        try {
            getSerial(this)
        } catch (e: Exception) {
            e.printStackTrace()
            exit()
        }
    }

    override fun draw() {
        getArduinoValues()
        println("pot: $potVal")
//        if (potVal != null) {
//
//        }
    }
}