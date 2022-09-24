package com.sapin.themovies.domain

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.Observer
import com.sapin.themovies.data.web.webds.UploadWebDS
import okhttp3.internal.readFieldOrNull
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UploadRepository @Inject constructor(private val uploadWebDS: UploadWebDS) {
    fun uploadImages(file: Uri, error:Observer<String>){
        uploadWebDS.uploadImage(error, file)
    }
}