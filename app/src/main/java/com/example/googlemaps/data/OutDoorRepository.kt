package com.example.googlemaps.data


import androidx.lifecycle.LiveData

interface OutdoorRepository {
    fun getAllActivities(): LiveData<List<Activity>>

    fun getAllLocations(): LiveData<List<Location>>
    /*
       Get  locations based on the activity Id
     */

    fun getActivityWithLocations(activityId: Int): LiveData<ActivityWithLocations>

    fun getLocationById(locationId: Int): Location

    fun getLocationWithActivities(locationId: Int): LiveData<LocationWithActivities>

    fun toggleActivityGeofence(id: Int): GeofencingChanges
}