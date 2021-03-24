package com.nicolettetovstashy.coral.data

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun coralDataMapFromCsv(csvFilename: String): List<DateCoralData> {
    val fileText = object {}.javaClass.getResource(csvFilename).readText()

    val rows: List<Map<String, String>> = csvReader().readAllWithHeader(fileText)
    val list = mutableListOf<DateCoralData>()

    for (row in rows) {
        val formatter = DateTimeFormatter.ofPattern("M/d/yyyy")
        val date = LocalDate.parse(row["Date"], formatter)

        list.add(DateCoralData(
            date = date,
            latitude = row["Latitude"]!!.toDouble(),
            longitude = row["Longitude"]!!.toDouble(),
            seaSurfaceTemperature = row["Sea_Surface_Temperature"]!!.toDouble(),
            hotSpots = row["HotSpots"]!!.toDouble(),
            degreeHeatingWeeks = row["Degree_Heating_Weeks"]!!.toDouble(),
            bleachingAlertArea = row["Bleaching_Alert_Area"]!!.toInt()
        ))
    }

    return list
}