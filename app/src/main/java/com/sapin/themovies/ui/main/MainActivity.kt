package com.sapin.themovies.ui.main

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.location.*
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.sapin.themovies.BuildConfig
import com.sapin.themovies.R
import com.sapin.themovies.data.db.model.LocationModel
import com.sapin.themovies.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){
    companion object{
        const val REQUEST_CODE=0
        private const val TAG = "CameraXApp"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS =
            mutableListOf (
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }
    private val CHANNEL_ID="CHANEL"
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    lateinit var mSettingsClient:SettingsClient
    lateinit var locationRequest:LocationRequest
    lateinit var locationSettingsRequest:LocationSettingsRequest
    lateinit var locationCallback:LocationCallback
    lateinit var location :Location
    var mRequestingLocationUpdates=false
    lateinit  var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)
        //requestPermision()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mSettingsClient = LocationServices.getSettingsClient(this)

        locationCallback= object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult?) {
                    super.onLocationResult(p0)
                    location=p0!!.lastLocation
                    //aqui estan mis datos
                    var latitude=location.latitude
                    var longitud=location.longitude
                    Log.d("LOCATION","latitude:"+location.latitude)
                    Log.d("LOCATION","longitud:"+longitud)
                   var date=Date()
                    var l= LocationModel(latitude,longitud,date.toString())
                    viewModel.saveLocation(l)
                setNotificate()
                }
        }

        locationRequest= LocationRequest.create()
            .setInterval(300000)
            .setFastestInterval(300000)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)

        locationSettingsRequest = LocationSettingsRequest.Builder().addLocationRequest(locationRequest).build()
        Dexter.withActivity(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object : PermissionListener{
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    startLocationUpdates()
                    mRequestingLocationUpdates=true
                }
                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    if(response!!.isPermanentlyDenied){
                        opensettings()
                    }
                }
                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    token!!.continuePermissionRequest()
                }
            }).check()


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView = binding.navView
       val  navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_Movies,
                R.id.nav_Location,
                R.id.nav_upload
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)



        ///permisos de camera
        // Request camera permissions
        if (allPermissionsGranted()) {
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_CODE -> if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                //map.isMyLocationEnabled=true
                else
                    Toast.makeText(baseContext,"para activar la localizacion ve ajustes y acepta los permisos",Toast.LENGTH_SHORT).show()
        }
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                Toast.makeText(baseContext,"Permisos obtorgaodo",Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }


    private fun opensettings(){
        var inten=Intent()
        inten.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        var uri= Uri.fromParts("package", BuildConfig.APPLICATION_ID,null)
        inten.data=uri
        inten.flags=Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(inten)
    }

    fun startLocationUpdates(){
        mSettingsClient.checkLocationSettings(locationSettingsRequest).addOnSuccessListener {
            fusedLocationClient.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper())
        }
    }

    private fun stopRemoveLocationUpdates(){
        fusedLocationClient.removeLocationUpdates(locationCallback).addOnCompleteListener{
            Log.d("LOCALIZACION","detenido la localizacion")
        }
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        if(mRequestingLocationUpdates)
            stopRemoveLocationUpdates()
    }



    fun setNotificate(){
        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Ubicacion guardada")
            .setContentText("se ha guardado la ubicacion en firebase")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(1, builder.build())
        }
    }
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }




    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

}