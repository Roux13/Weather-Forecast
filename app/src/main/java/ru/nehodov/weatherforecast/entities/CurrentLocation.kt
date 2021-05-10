package ru.nehodov.weatherforecast.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CurrentLocation")
data class CurrentLocation(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val timezone: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
)