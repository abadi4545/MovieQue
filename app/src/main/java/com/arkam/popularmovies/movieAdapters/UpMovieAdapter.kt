package com.arkam.popularmovies.movieAdapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.arkam.popularmovies.R
import com.arkam.popularmovies.movieActivites.ActivitySecond
import com.arkam.popularmovies.model.Movie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_1.view.*

class UpMovieAdapter(val context: Context, val namelist: List<Movie>, val check: Boolean) :
    RecyclerView.Adapter<UpMovieAdapter.myviewholder>() {

    val baseURL = "https://image.tmdb.org/t/p/w1280/"


    override fun getItemCount(): Int {
        if (check == false)
            if (namelist != null) {
                return namelist.size

            }

        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myviewholder {

        var li = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = li.inflate(R.layout.layout_2, parent, false)
        return myviewholder(itemView)

    }

    override fun onBindViewHolder(holder: myviewholder, position: Int) {

        val item1 = this.namelist[position]
        holder.itemView.ltView.text = item1.original_title
        val target = item1.backdrop_path
        Picasso.get().load(baseURL + target).resize(300, 180).into(holder.itemView.liView)
        holder.itemView.parentLayout.setOnClickListener {

            val intent = Intent(context, ActivitySecond::class.java)
            intent.putExtra("id", item1.id)
            intent.putExtra("type", "Movie")
            ContextCompat.startActivity(context, intent, null)
        }
    }

    inner class myviewholder(itemView: View) : RecyclerView.ViewHolder(itemView)


}