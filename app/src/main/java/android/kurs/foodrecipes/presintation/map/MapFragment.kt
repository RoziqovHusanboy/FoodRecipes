package android.kurs.foodrecipes.presintation.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.kurs.foodrecipes.R
import android.kurs.foodrecipes.databinding.FragmentMapBinding
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var bindiing: FragmentMapBinding
    private lateinit var myMap: GoogleMap
    private val viewModel by viewModels<MapViewModel>()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var currentLocation: Location? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindiing = FragmentMapBinding.inflate(inflater)
        return bindiing.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        val mapFragment = childFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    @SuppressLint("MissingPermission", "UseCompatLoadingForDrawables")
    override fun onMapReady(map: GoogleMap) {
        myMap = map
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            myMap.isMyLocationEnabled = true
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    currentLocation = location
                    myMap.isMyLocationEnabled = true
                }
            }
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }

        viewModel.location.observe(viewLifecycleOwner) {
            for (location in it) {
                val latLng = LatLng(location.latitute, location.longitute)
                val marker = myMap.addMarker(MarkerOptions().position(latLng))
                marker?.position = latLng
                marker?.title = location.title

                val height = 150
                val width = 150
                val bitmapdraw = resources.getDrawable(R.drawable.image_saladd) as BitmapDrawable
                val b = bitmapdraw.bitmap
                val smallMarker = Bitmap.createScaledBitmap(b, width, height, false)
                marker?.setIcon(BitmapDescriptorFactory.fromBitmap(smallMarker))

                val moveCamera = CameraUpdateFactory.newLatLngZoom(latLng, 12f)
                myMap.animateCamera(moveCamera)

            }
            myMap.setOnMarkerClickListener { clickedMarker ->
                var title = String()
                var desc = String()
                clickedMarker.title?.let {
                    title = it
                }
                clickedMarker.snippet?.let {
                    desc = it
                }
                it.forEach {
                    if (it.title == title){
                        openDialog(title, clickedMarker,it.drawable)
                    }
                }

                true
            }
        }
    }

    private fun openDialog(newTitle: String,  marker: Marker,imageView: Int) {

        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(R.layout.item_bottom_sheet)
        val title = dialog.findViewById<TextView>(R.id.textViewTitle)
        val imageview = dialog.findViewById<ImageView>(R.id.imageviewBottomSheet)
        val textViewDistance = dialog.findViewById<TextView>(R.id.textViewDistance)
        val button = dialog.findViewById<Button>(R.id.button)
        imageview?.setImageResource(imageView)
        title?.text = newTitle

        val markerPosition = marker.position
        val distance = currentLocation?.let {
            it.distanceTo(Location(LocationManager.GPS_PROVIDER).apply {
                latitude = markerPosition.latitude
                longitude = markerPosition.longitude
            })
        }

        val distanceToKilloMetr = distance?.let { it / 1000 }
        val distanceText = String.format("%.2f", distanceToKilloMetr)
        textViewDistance?.text =
            requireContext().getString(R.string.item_bottom_sheet_text_distance, distanceText)

        button?.setOnClickListener {
            currentLocation?.let {
                drawPolyline(it, markerPosition)
            }
        }
        dialog.show()

    }

    private fun drawPolyline(startLocation: Location, endLatLng: LatLng) {
        val gmmIntentUri =
            Uri.parse("https://www.google.com/maps/dir/?api=1&origin=${startLocation.latitude},${startLocation.longitude}&destination=${endLatLng.latitude},${endLatLng.longitude}")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, enable My Location layer and move camera
                myMap.isMyLocationEnabled = true
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        val latLng = LatLng(location.latitude, location.longitude)
                        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                    }
                }
            } else {
                // Permission denied
                Toast.makeText(requireContext(), "Location permission denied", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 1
    }
}

