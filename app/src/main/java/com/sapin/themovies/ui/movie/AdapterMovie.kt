package com.sapin.themovies.ui.movie

import com.sapin.themovies.R
import com.sapin.themovies.data.db.model.Movie




import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterMovie(private val dataSet: List<Movie>) :
    RecyclerView.Adapter<AdapterMovie.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView
        val languaje:TextView
        val mview:View
        val date : TextView
        val originalTitle:TextView

        init {
            mview=view
            // Define click listener for the ViewHolder's View.
            title=view.findViewById(R.id.txttitle)
            date = view.findViewById(R.id.txtDate)
            originalTitle=view.findViewById(R.id.txtOrinal)
            languaje=view.findViewById(R.id.txtLanguaje)


        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.cardviewmovies, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.title.text = dataSet.get(position).title
        viewHolder.languaje.text= dataSet.get(position).originalLanguage
        viewHolder.date.text=dataSet.get(position).releaseDate
        viewHolder.originalTitle.text=dataSet.get(position).originalTitle

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
