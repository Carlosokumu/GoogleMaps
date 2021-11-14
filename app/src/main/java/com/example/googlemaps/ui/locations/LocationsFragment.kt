package com.example.googlemaps.ui.locations

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.googlemaps.R
import com.example.googlemaps.data.Location
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.psdemo.outdoorexplorer.ui.locations.LocationsAdapter
import com.psdemo.outdoorexplorer.ui.locations.LocationsViewModel
import kotlinx.android.synthetic.main.fragment_locations.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

class LocationsFragment : Fragment(), LocationsAdapter.OnClickListener {
    private lateinit var adapter: LocationsAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View =
            inflater.inflate(R.layout.fragment_locations, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val locationsViewModel = ViewModelProvider(this)
                .get(LocationsViewModel::class.java)

        adapter = LocationsAdapter(this)
        listLocations.adapter = adapter

        arguments?.let { bundle ->
            val passedArguments = LocationsFragmentArgs.fromBundle(bundle)
            if (passedArguments.activityId == 0) {
                locationsViewModel.allLocations.observe(viewLifecycleOwner, Observer {
                    adapter.setLocations(it)
                })
            } else {
                locationsViewModel.locationsWithActivity(passedArguments.activityId)
                        .observe(viewLifecycleOwner, Observer {
                            adapter.setLocations(it.locations)
                        })
            }
        }

        getCurrentLocation()
    }

    @SuppressLint("MissingPermission")
    @AfterPermissionGranted(RC_LOCATION)
    private fun getCurrentLocation() {
        if (EasyPermissions.hasPermissions(requireContext(),android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
            if (ActivityCompat.checkSelfPermission(requireContext(),Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            fusedLocationClient.lastLocation.addOnSuccessListener { location: android.location.Location? ->
                if (location != null) {
                    adapter.setCurrentLocation(location)
                }
            }
        } else {

            Snackbar.make(requireView(), getString(R.string.locations_snackbar), Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok) {
                        EasyPermissions.requestPermissions(
                                this,
                                getString(R.string.locations_rationale),
                                RC_LOCATION,
                                android.Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    }
                    .show()


        }
    }

    override fun onClick(id: Int) {
        val action = LocationsFragmentDirections
                .actionNavigationLocationsToNavigationLocation()
        action.locationId = id
        val navController = Navigation.findNavController(requireView())
        navController.navigate(action)
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    companion object {
        const val RC_LOCATION = 10
    }
}
