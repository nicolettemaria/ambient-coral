package com.nicolettetovstashy.coral.data

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import java.time.LocalDate
import java.time.format.DateTimeFormatter


typealias CoralDataMap = LinkedHashMap<LocalDate, DateCoralData>

fun coralDataMapFromCsv(csvFilename: String): CoralDataMap {
    val fileText = object {}.javaClass.getResource(csvFilename).readText()

    val rows: List<Map<String, String>> = csvReader().readAllWithHeader(fileText)
    val map = LinkedHashMap<LocalDate, DateCoralData>()

    for (row in rows) {
        val formatter = DateTimeFormatter.ofPattern("M/d/yyyy")
        val date = LocalDate.parse(row["Date"], formatter)

        map[date] = DateCoralData(
            latitude = row["Latitude"]!!.toDouble(),
            longitude = row["Longitude"]!!.toDouble(),
            seaSurfaceTemperature = row["Sea_Surface_Temperature"]!!.toDouble(),
            hotSpots = row["HotSpots"]!!.toDouble(),
            degreeHeatingWeeks = row["Degree_Heating_Weeks"]!!.toDouble(),
            bleachingAlertArea = row["Bleaching_Alert_Area"]!!.toInt()
        )
    }

    return map
}