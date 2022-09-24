package com.sapin.themovies.ui.upload

import android.R.attr.data
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.google.android.material.internal.ContextUtils.getActivity
import com.sapin.themovies.domain.UploadRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UploadViewModel @Inject constructor(private val repository: UploadRepository) :ViewModel() {
    val errorUpload:MutableLiveData<String>

    init {
        errorUpload= MutableLiveData()
    }
    fun uploadPhoto(file:Uri){
        repository.uploadImages(file,errorUpload())
    }


    fun errorUpload():Observer<String>{
        return Observer {
            errorUpload.postValue(it)
        }
    }
}