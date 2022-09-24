package com.sapin.themovies.ui.location

import android.widget.MultiAutoCompleteTextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.QuerySnapshot
import com.sapin.themovies.data.db.model.LocationModel
import com.sapin.themovies.domain.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LocationViewModel @Inject constructor(private val repository: LocationRepository) :ViewModel(){
    val responseLocations:MutableLiveData<List<LocationModel>>
    val errorLocationsResponse:MutableLiveData<String>
    init {
        responseLocations= MutableLiveData()
        errorLocationsResponse=MutableLiveData()
        getLocations()
    }
    fun getLocations() =repository.readLocations(setLocations(), setErrorReadLocations())

   private fun setLocations():Observer<QuerySnapshot>{
       return  Observer{
           var list=ArrayList<LocationModel>()
            for (document in it.documents) {
                var location=LocationModel(document.getDouble("latitude")!!, document.getDouble("longitude")!!,document.getString("fecha")!!)
                list.add(location)
            }
           responseLocations.value=list
        }
    }
    private fun setErrorReadLocations():Observer<String> =Observer { errorLocationsResponse.postValue(it) }

}