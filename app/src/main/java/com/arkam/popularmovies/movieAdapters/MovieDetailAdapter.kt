package com.arkam.popularmovies.movieAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arkam.popularmovies.R
import com.arkam.popularmovies.model.Movie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_second.view.*

class MovieDetailAdapter(val context: Context, val namelist: List<Movie>, val check: Boolean) :
    RecyclerView.Adapter<MovieDetailAdapter.myviewholder>() {

    val baseURL = "https://image.tmdb.org/t/p/w342/"

    class myviewholder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun getItemCount(): Int {
        if (check == false)
            if (namelist != null) {
                return namelist.size

            }

        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myviewholder {

        var li = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = li.inflate(R.layout.activity_second, parent, false)
        return myviewholder(itemView)

    }

    override fun onBindViewHolder(holder: myviewholder, position: Int) {

        val item1 = this.namelist[position]


        val target = item1.poster_path
        Picasso.get().load(baseURL + target).into(holder.itemView.iview)


    }


}