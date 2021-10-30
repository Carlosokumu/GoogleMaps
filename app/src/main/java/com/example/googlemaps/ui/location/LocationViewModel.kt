package com.psdemo.outdoorexplorer.ui.location

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.googlemaps.data.OutdoorRepository
import com.example.googlemaps.data.OutdoorRoomDatabase
import com.example.googlemaps.data.OutdoorRoomRepository

class LocationViewModel(application: Application) : AndroidViewModel(application) {
    private val outdoorRepository: OutdoorRepository

    init {
        val outdoorDao = OutdoorRoomDatabase.getInstance(application).outdoorDao()
        outdoorRepository = OutdoorRoomRepository(outdoorDao)
    }

    fun getLocation(locationId: Int) =
        outdoorRepository.getLocationWithActivities(locationId)
}