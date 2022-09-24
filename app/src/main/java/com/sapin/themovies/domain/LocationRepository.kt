package com.sapin.themovies.domain

import androidx.lifecycle.Observer
import com.google.firebase.firestore.QuerySnapshot
import com.sapin.themovies.data.db.model.LocationModel
import com.sapin.themovies.data.web.webds.LocationWebDS
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LocationRepository @Inject constructor(private val locationWebDS: LocationWebDS) {
    fun sendLocation(locationModel: LocationModel, observer: Observer<String>,error:Observer<String>) = locationWebDS.sendLocation(locationModel,observer,error)

    fun readLocations(observer: Observer<QuerySnapshot>,error: Observer<String>) = locationWebDS.readLocations(observer, error)

}