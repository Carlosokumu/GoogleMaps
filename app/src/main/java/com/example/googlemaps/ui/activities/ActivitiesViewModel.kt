package com.example.googlemaps.ui.activities

import com.example.googlemaps.data.OutdoorRepository
import com.example.googlemaps.data.OutdoorRoomDatabase
import com.example.googlemaps.data.OutdoorRoomRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel


class ActivitiesViewModel(application: Application) : AndroidViewModel(application) {
    private val outdoorRepository: OutdoorRepository

    init {
        val outdoorDao = OutdoorRoomDatabase.getInstance(application).outdoorDao()
        outdoorRepository = OutdoorRoomRepository(outdoorDao)
    }

    val allActivities = outdoorRepository.getAllActivities()

    fun toggleGeofencing(id: Int) = outdoorRepository.toggleActivityGeofence(id)
}