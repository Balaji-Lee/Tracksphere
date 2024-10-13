package com.example.tracksphere.model

import android.graphics.Color
data class Vehicle(
    val vehicleNumber: String,
    val status: String,
    val speedOrTime: String,
    val statusColor: androidx.compose.ui.graphics.Color
)