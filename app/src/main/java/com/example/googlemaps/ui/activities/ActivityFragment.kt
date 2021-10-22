package com.example.googlemaps.ui.activities

package com.psdemo.outdoorexplorer.ui.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.psdemo.outdoorexplorer.R
import kotlinx.android.synthetic.main.fragment_activities.*

class ActivitiesFragment : Fragment(), ActivitiesAdapter.OnClickListener {
    private lateinit var activitiesViewModel: ActivitiesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(R.layout.fragment_activities, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activitiesViewModel = ViewModelProvider(this)
            .get(ActivitiesViewModel::class.java)

        val adapter = ActivitiesAdapter(this)
        listActivities.adapter = adapter

        activitiesViewModel.allActivities.observe(viewLifecycleOwner, Observer {
            adapter.setActivities(it)
        })
    }

    override fun onClick(id: Int, title: String) {
        val action = ActivitiesFragmentDirections
            .actionNavigationActivitiesToNavigationLocations()
        action.activityId = id
        action.title = "Locations with $title"
        val navController = Navigation.findNavController(requireView())
        navController.navigate(action)
    }

    override fun onGeofenceClick(id: Int) {
        TODO("Not yet implemented")
    }
}
