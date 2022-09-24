package com.sapin.themovies.ui.location

import android.view.View
import com.sapin.themovies.data.db.model.LocationModel

interface OnClickListenerLocation {
    fun OnClickListenerLocation(locationModel: LocationModel)
}