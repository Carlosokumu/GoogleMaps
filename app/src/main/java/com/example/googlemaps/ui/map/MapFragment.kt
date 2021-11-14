package com.psdemo.outdoorexplorer.ui.map

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.googlemaps.R
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_map, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mapViewModel = ViewModelProvider(this)
            .get(MapViewModel::class.java)
        val mapFragementManager=childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragementManager.getMapAsync{ map ->
            run {
                val bay = LatLng(37.68, -122.42)
                map.moveCamera(CameraUpdateFactory.zoomTo(10f))
                map.moveCamera(CameraUpdateFactory.newLatLng(bay))
                mapViewModel.allLocations.observe(viewLifecycleOwner, Observer {
                    for (location in it){
                        val point=LatLng(location.latitude,location.longitude)
                        map.addMarker(MarkerOptions().position(point).title(location.title).snippet("Hours: ${location.hours}").icon(
                            getBitmapFromVector(R.drawable.star_black_24,R.color.colorAccent)
                        ).alpha(0.7f))
                    }
                })
                map.uiSettings.isZoomControlsEnabled=true
                map.uiSettings.isTiltGesturesEnabled=false
            }
        }

    }

   private fun getBitmapFromVector(
       @DrawableRes vectorResourceId: Int,
       @ColorRes colorResourceId: Int
    ): BitmapDescriptor {
        val vectorDrawable = resources.getDrawable(vectorResourceId, requireContext().theme)
           ?: return BitmapDescriptorFactory.defaultMarker()

        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
       )

        val canvas = Canvas(bitmap)
       vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        DrawableCompat.setTint(
            vectorDrawable,
            ResourcesCompat.getColor(
               resources,
                colorResourceId, requireContext().theme
            )
        )
        vectorDrawable.draw(canvas)
      return BitmapDescriptorFactory.fromBitmap(bitmap)
 }
}
