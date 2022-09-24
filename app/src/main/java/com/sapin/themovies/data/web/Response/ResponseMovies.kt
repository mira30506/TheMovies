package com.sapin.themovies.data.web.Response

import com.google.gson.annotations.SerializedName
import com.sapin.themovies.data.db.model.Movie

data class ResponseMovies (
    @SerializedName("page"          ) var page         : Int?               = null,
    @SerializedName("results"       ) var results      : List<Movie> = arrayListOf(),
    @SerializedName("total_pages"   ) var totalPages   : Int?               = null,
    @SerializedName("total_results" ) var totalResults : Int?               = null
    )