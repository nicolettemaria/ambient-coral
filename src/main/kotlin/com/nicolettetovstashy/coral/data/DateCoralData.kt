package com.nicolettetovstashy.coral.data

import java.time.LocalDate

data class DateCoralData(
    val date: LocalDate,
    val latitude: Double,
    val longitude: Double,
    val seaSurfaceTemperature: Double,
    val hotSpots: Double,
    val degreeHeatingWeeks: Double,
    val bleachingAlertArea: Int
)