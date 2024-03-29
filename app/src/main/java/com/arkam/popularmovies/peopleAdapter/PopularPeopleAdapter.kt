package com.arkam.popularmovies.peopleAdapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

import com.arkam.popularmovies.model.people
import com.arkam.popularmovies.peopleActivities.MainActivityPeopleSecond
import com.arkam.popularmovies.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_1.view.parentLayout
import kotlinx.android.synthetic.main.peoplelayout.view.*

class PopularPeopleAdapter(val context: Context, val namelist: List<people>, val check: Boolean) :
    RecyclerView.Adapter<PopularPeopleAdapter.myviewholder>() {

    val baseURL = "https://image.tmdb.org/t/p/original/"


    override fun getItemCount(): Int {
        if (check == false)
            if (namelist != null) {
                return namelist.size

            }

        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myviewholder {

        var li = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = li.inflate(R.layout.peoplelayout, parent, false)
        return myviewholder(itemView)

    }

    override fun onBindViewHolder(holder: myviewholder, position: Int) {

        val item1 = this.namelist[position]
        holder.itemView.ltViewpopular.text = item1.name
        holder.itemView.tvpopularity.text = item1.popularity.toString()
        val target = item1.profile_path
        Picasso.get().load(baseURL + target).resize(160, 165).into(holder.itemView.liViewpopular)

        holder.itemView.parentLayout.setOnClickListener {

            val intent = Intent(context, MainActivityPeopleSecond::class.java)
            intent.putExtra("id", item1.id)
            intent.putExtra("type", "People")
            ContextCompat.startActivity(context, intent, null)
        }

    }

    inner class myviewholder(itemView: View) : RecyclerView.ViewHolder(itemView)

}