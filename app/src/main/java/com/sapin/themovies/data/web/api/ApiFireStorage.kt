package com.sapin.themovies.data.web.api

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.core.net.toFile
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ApiFireStorage @Inject constructor(private val storage: FirebaseStorage) {
    fun uploadImage(file:Uri,observer: Observer<String>){
        var referencia=storage.getReference()
        var filepath=referencia.child("fotos").child(file.lastPathSegment!!)
        filepath.putFile(file).addOnSuccessListener {
            observer.onChanged("Se ha insertado exitosamente")
        }
    }
}