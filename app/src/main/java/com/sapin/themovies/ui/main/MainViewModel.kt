package com.sapin.themovies.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.sapin.themovies.data.db.model.LocationModel
import com.sapin.themovies.domain.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val repository: LocationRepository) : ViewModel() {
    var error:MutableLiveData<String>
    var result:MutableLiveData<String>

    init {
        error=MutableLiveData()
        result=MutableLiveData()
    }
    fun saveLocation(locationModel: LocationModel){
        repository.sendLocation(locationModel,result(),error())
    }
    fun result():Observer<String>{
        return Observer {
            result.postValue(it)
        }
    }


    fun error(): Observer<String>{
        return Observer {
            error.postValue(it)
        }
    }
}