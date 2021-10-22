package com.psdemo.outdoorexplorer.ui.locations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.psdemo.outdoorexplorer.R
import kotlinx.android.synthetic.main.fragment_locations.*

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
    }

    override fun onClick(id: Int) {
        val action = LocationsFragmentDirections
            .actionNavigationLocationsToNavigationLocation()
        action.locationId = id
        val navController = Navigation.findNavController(requireView())
        navController.navigate(action)
    }
}
