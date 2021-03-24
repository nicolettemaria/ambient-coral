package com.nicolettetovstashy.coral

import com.nicolettetovstashy.coral.data.coralDataMapFromCsv
import com.nicolettetovstashy.coral.light.LightUtil
import com.nicolettetovstashy.coral.sketch.Sketch

fun main(args: Array<String>) {
    val coralData = coralDataMapFromCsv("/hawaii_coral_dates_fixed.csv")
    val lightUtil = LightUtil()
    Sketch.run(lightUtil, coralData)
}