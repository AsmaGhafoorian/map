package com.test.presentation.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.test.presentation.*
import com.test.presentation.model.RegisterBodyModel
import com.test.presentation.navigation.mapNavigator
import com.test.presentation.navigation.registerNavigator
import com.test.presentation.ui.base.BaseActivity
import com.test.presentation.ui.register.RegisterViewModel
import com.test.presentation.utils.REGISTER_DATA
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject


class MapActivity : BaseActivity(), OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {



    var data : RegisterBodyModel ? = null
    private lateinit var mMap: GoogleMap
    private var mGoogleApiClient: GoogleApiClient? = null
    private var REQUEST_LOCATION_CODE = 101
    private var location: Location? = null
    private var mLocationRequest: LocationRequest? = null
    private var latLng: LatLng? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var service: LocationManager? = null
    private var enabled: Boolean? = null
    var latlng : LatLng? = null

    var permission = PackageManager.PERMISSION_DENIED

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        getAppInjector().inject(this)
        init()
    }

    fun init(){

        registerToolbar()
        locationTxt.visible()

        data = intent.getSerializableExtra(REGISTER_DATA) as RegisterBodyModel

        val mapFragment = supportFragmentManager.findFragmentById(R.id.addressMap) as SupportMapFragment
        mapFragment.getMapAsync(this)

        confirmBtn.setOnClickListener {
            data!!.lat = location?.latitude
            data!!.lng = location?.longitude
            Log.d("data=======",location?.latitude.toString())

            register()
        }


    }


    ////////////////////////////////////////////// Show Locations On Map////////////////////////////
    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {

        if(checkGPSEnabled()) {
            mMap = googleMap
            mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            mMap.uiSettings.isZoomControlsEnabled = true
           mMap.setOnCameraIdleListener(object : GoogleMap.OnCameraIdleListener {
            override fun onCameraIdle() {
                latLng = LatLng(mMap.cameraPosition.target.latitude, mMap.cameraPosition.target.longitude)
            }
        })

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ( ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient()
                mGoogleApiClient?.connect()
                mMap.isMyLocationEnabled = true

            } else {
                checkPermission()

            }
        }else{
            buildGoogleApiClient()
            mMap.isMyLocationEnabled = true
        }
        }
    }


    @SuppressLint("MissingPermission")
    private fun checkGPSEnabled(): Boolean {
        if (!isLocationEnabled())
            showAlert()
        return isLocationEnabled()
    }

    private fun showAlert() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " + "use this app")
                .setPositiveButton("Location Settings") { paramDialogInterface, paramInt ->
                    val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(myIntent)
                }
                .setNegativeButton("Cancel") { paramDialogInterface, paramInt -> }
        dialog.show()
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }


    fun checkPermission(){

        if ( ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_CODE)
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_LOCATION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        permission = PackageManager.PERMISSION_GRANTED
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient()
                            mGoogleApiClient?.connect()

                        }
                        mMap.isMyLocationEnabled = true

                    } else {
                    }


                } else {
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show()
                }
                return
            }
        }
    }

    override fun onLocationChanged(location: Location?) {

    }


    override fun onConnectionFailed(p0: ConnectionResult) {
        val t = p0
    }


    @Synchronized
    fun buildGoogleApiClient() {

        mGoogleApiClient = GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()

    }


    @SuppressLint("MissingPermission")
    override fun onConnected(p0: Bundle?) {
        startLocationUpdates()
        getLocation()


    }

    override fun onConnectionSuspended(p0: Int) {
        val t = p0
    }


    private fun startLocationUpdates() {
        mLocationRequest = LocationRequest.create()
        mLocationRequest!!.interval = 1000
        mLocationRequest!!.fastestInterval = 1000
        mLocationRequest!!.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this)
    }


    @SuppressLint("MissingPermission")
    private fun getLocation() {
        location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient)


        if (location != null) {
            latlng = LatLng(location!!.latitude, location!!.longitude)
        } else {
            Toast.makeText(this, getString(R.string.location_error), Toast.LENGTH_SHORT).show()
            return
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng))
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17f))

    }

    ////////////////////////////////////////////// Post User Data////////////////////////////

    @Inject
    lateinit var navigator: mapNavigator
    private fun register(){
        withViewModel<RegisterViewModel>(viewModelFactory){
            data?.let { getConfirmationCode(it, this@MapActivity) }
            observe(submit, ::getConfirmationCodeResponse)
        }
    }

    private fun getConfirmationCodeResponse(data: Data<Any>?) {
        data?.let {

            when (it.dataState) {

                DataState.LOADING -> {
                    Log.d("=========>", "Loading")
                    showPagesLoading()
                }
                DataState.SUCCESS -> {
                    Log.d("=========>", "Success")
                    hidePagesLoading()
                    it.data?.let {

                        navigator.navigateToAddressActivity(this)

                    }!!
                }
                DataState.ERROR -> {
                    hidePagesLoading()
                    Toast.makeText(this, data.message, Toast.LENGTH_LONG).show()
                    Log.d("=========>", "Error " + data.message.toString())

                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}