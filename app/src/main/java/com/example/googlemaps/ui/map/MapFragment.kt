package com.psdemo.outdoorexplorer.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.googlemaps.R

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

    }

//    private fun getBitmapFromVector(
//        @DrawableRes vectorResourceId: Int,
//        @ColorRes colorResourceId: Int
//    ): BitmapDescriptor {
//        val vectorDrawable = resources.getDrawable(vectorResourceId, requireContext().theme)
//            ?: return BitmapDescriptorFactory.defaultMarker()
//
//        val bitmap = Bitmap.createBitmap(
//            vectorDrawable.intrinsicWidth,
//            vectorDrawable.intrinsicHeight,
//            Bitmap.Config.ARGB_8888
//        )
//
//        val canvas = Canvas(bitmap)
//        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
//        DrawableCompat.setTint(
//            vectorDrawable,
//            ResourcesCompat.getColor(
//                resources,
//                colorResourceId, requireContext().theme
//            )
//        )
//        vectorDrawable.draw(canvas)
//        return BitmapDescriptorFactory.fromBitmap(bitmap)
//    }
}
