package com.psdemo.outdoorexplorer.ui.map

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.googlemaps.data.OutdoorRepository
import com.example.googlemaps.data.OutdoorRoomDatabase
import com.example.googlemaps.data.OutdoorRoomRepository
import com.psdemo.outdoorexplorer.data.OutdoorRepository
import com.psdemo.outdoorexplorer.data.OutdoorRoomDatabase
import com.psdemo.outdoorexplorer.data.OutdoorRoomRepository

class MapViewModel(application: Application) : AndroidViewModel(application) {
    private val outdoorRepository: OutdoorRepository

    init {
        val outdoorDao = OutdoorRoomDatabase.getInstance(application).outdoorDao()
        outdoorRepository = OutdoorRoomRepository(outdoorDao)
    }

    val allLocations = outdoorRepository.getAllLocations()
}