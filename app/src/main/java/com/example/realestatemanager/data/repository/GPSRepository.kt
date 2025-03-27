package com.example.realestatemanager.data.repository

import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.annotation.NonNull
import androidx.annotation.RequiresPermission
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.realestatemanager.AppSingleton
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.squareup.okhttp.Dispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class GPSRepository() {

    val fusedClient = LocationServices.getFusedLocationProviderClient(AppSingleton.applicationInstance)

    companion object {
        private const val LOCATION_REQUEST_INTERVAL_MS : Long = 20_000
        private const val SMALLEST_DISPLACEMENT_METER = 50f
    }

    @RequiresPermission(anyOf = ["android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"])
    fun getLocationUpdate(): Flow<Location> = callbackFlow{
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, LOCATION_REQUEST_INTERVAL_MS)
            .setMinUpdateDistanceMeters(SMALLEST_DISPLACEMENT_METER)
            .build()
        val locationCallback = object : LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                locationResult?.lastLocation?.let { trySend(it) }
            }
        }

        fusedClient.requestLocationUpdates(locationRequest, locationCallback, null)

        awaitClose{
            fusedClient.removeLocationUpdates(locationCallback)
        }
    }

    @RequiresPermission(anyOf = ["android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"])
    suspend fun getLastLocation(): Location?{
        return withContext(Dispatchers.IO){
            fusedClient.lastLocation.await()
        }
    }

}
