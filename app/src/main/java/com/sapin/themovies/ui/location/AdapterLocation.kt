package com.sapin.themovies.ui.location

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sapin.themovies.R
import com.sapin.themovies.data.db.model.LocationModel
import com.sapin.themovies.data.db.model.Movie


class AdapterLocation(private val dataSet: List<LocationModel>,private val listener:OnClickListenerLocation) :
    RecyclerView.Adapter<AdapterLocation.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val date: TextView
        val latitude: TextView
        val mview: View
        val longitude : TextView
        init {
            mview=view
            latitude=view.findViewById(R.id.txtlatitude)
            date = view.findViewById(R.id.txtDate)
            longitude=view.findViewById(R.id.txtlongitude)
        }
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.cardviewlocation, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.longitude.text = dataSet.get(position).longitude.toString()
        viewHolder.latitude.text= dataSet.get(position).latitude.toString()
        viewHolder.date.text=dataSet.get(position).fecha
        viewHolder.mview.setOnClickListener {
            listener.OnClickListenerLocation(dataSet.get(position))
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}