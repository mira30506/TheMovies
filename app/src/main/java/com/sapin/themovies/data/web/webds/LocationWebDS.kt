package com.sapin.themovies.data.web.webds

import androidx.lifecycle.Observer
import com.google.firebase.firestore.QuerySnapshot
import com.sapin.themovies.data.db.model.LocationModel
import com.sapin.themovies.data.web.api.ApiFirebase
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LocationWebDS @Inject constructor(private val apiFirebase: ApiFirebase) {
    fun sendLocation(locationModel: LocationModel, observer: Observer<String>,error:Observer<String>)=( apiFirebase.sendLocation(locationModel,observer,error))

    fun readLocations(observer: Observer<QuerySnapshot>,error: Observer<String>)=( apiFirebase.readLocations(observer,error))
}