package com.sapin.themovies.data.web.api

import androidx.lifecycle.Observer
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.sapin.themovies.data.db.model.LocationModel
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ApiFirebase @Inject constructor(private val firestore: FirebaseFirestore) {

    fun sendLocation(locationModel: LocationModel, observer: Observer<String>,error: Observer<String>){
        firestore.collection("Location")
            .add(locationModel)
            .addOnSuccessListener {
                observer.onChanged(it.id)
            }
            .addOnFailureListener {
                error.onChanged(it.toString())
            }
    }
    fun readLocations(observer: Observer<QuerySnapshot>,error:Observer<String>){
        firestore.collection("Location")
            .get()
            .addOnCompleteListener{
                if(it.isSuccessful)
                    observer.onChanged(it.result)
                else
                    error.onChanged(it.exception.toString())
            }
    }
}