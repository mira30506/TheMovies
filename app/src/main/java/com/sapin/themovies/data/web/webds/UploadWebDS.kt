package com.sapin.themovies.data.web.webds

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.Observer
import com.sapin.themovies.data.web.api.ApiFireStorage
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UploadWebDS @Inject constructor(private val apiFireStorage: ApiFireStorage) {
    fun uploadImage(error:Observer<String>,file:Uri){
        apiFireStorage.uploadImage(file,error)
    }
}