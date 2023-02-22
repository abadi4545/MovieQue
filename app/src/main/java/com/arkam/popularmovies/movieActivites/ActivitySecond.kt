package com.arkam.popularmovies.movieActivites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.arkam.popularmovies.Favourite
import com.arkam.popularmovies.FavouriteDatabase
import com.arkam.popularmovies.network.PopInterface
import com.arkam.popularmovies.R
import com.arkam.popularmovies.model.*
import com.arkam.popularmovies.movieAdapters.movieadapter
import com.arkam.popularmovies.movieAdapters.moviecastdapter
import com.arkam.popularmovies.movieAdapters.VideoAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_second.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Suppress("DEPRECATION")
class ActivitySecond : AppCompatActivity() {


    val api_key: String = "0e03d86efe00ea1a1e1dd7d2a4717ba1"
    var maxLimit: Int = 996
    val retrofit = Retrofit.Builder().baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val baseURL = "https://image.tmdb.org/t/p/w780/"
    val service = retrofit.create(PopInterface::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
//        val toolbar: Toolbar = findViewById(R.id.toolbar)
//        setSupportActionBar(toolbar)
        textView4.isVisible = false
        textView5.isVisible = false
        textView6.isVisible = false


        val id = intent.getStringExtra("id")?.toInt()

        val type = intent.getStringExtra("type")


        val db: FavouriteDatabase by lazy {
            Room.databaseBuilder(
                this,
                FavouriteDatabase::class.java,
                "Fav.db"
            ).allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }


        var fill = 0


        val imgfav = findViewById<ImageView>(R.id.imagefav)

        val isFav = db.FavDao().isFavourite(id.toString())

        if (isFav == null) {
            fill = 0
            imgfav.setImageResource(R.drawable.ic_favorite_border)
        } else {
            fill = 1
            imgfav.setImageResource(R.drawable.ic_favorite_fill)
        }

        imgfav.setOnClickListener {

            if (fill == 1) {
                imgfav.setImageResource(R.drawable.ic_favorite_border)
                db.FavDao().delete(id.toString())
                Toast.makeText(this, "Removed from favourite", Toast.LENGTH_SHORT).show()
                fill = 0

            } else {

                id?.let { it1 ->
                    service.getmovies(it1, api_key).enqueue(object : Callback<MovieSearch> {
                        override fun onFailure(call: Call<MovieSearch>, t: Throwable) {
                            Log.d("MoviesDagger", t.toString())
                        }


                        override fun onResponse(
                            call: Call<MovieSearch>,
                            response: Response<MovieSearch>
                        ) {

                            val data = response.body()
                            if (response.isSuccessful) {
                                var obj = Favourite(
                                    movie_id = id.toString(),
                                    path = data!!.poster_path.toString()
                                )
                                db.FavDao().insertRow(obj)
                                imgfav.setImageResource(R.drawable.ic_favorite_fill)
                                Toast.makeText(
                                    this@ActivitySecond,
                                    "Added to favourite",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }


                        }
                    })
                }


            }
        }


        if (id != null) {
            service.getmovies(id, api_key).enqueue(object : Callback<MovieSearch> {
                override fun onFailure(call: Call<MovieSearch>, t: Throwable) {
                    Log.d("MoviesDagger", t.toString())
                }


                override fun onResponse(
                    call: Call<MovieSearch>,
                    response: Response<MovieSearch>
                ) {

                    val data = response.body()





                    if (data != null) {
                        Picasso.get().load(baseURL + data.backdrop_path).resize(413, 200)
                            .into(iview)
                        Picasso.get().load(baseURL + data.poster_path).into(imageview2)
                    }
                    tv7.text = data?.original_title
                    tv1.text = "Release Date    " + data?.release_date
                    tv3.text = data?.vote_average + "/10"
                    tvoverview.text = data?.overview


                }
            })
        }

        if (id != null) {
            service.getcast(id, api_key).enqueue(object : Callback<MovieCastResponse> {
                override fun onFailure(call: Call<MovieCastResponse>, t: Throwable) {
                    Log.d("MoviesDagger", t.toString())
                }


                override fun onResponse(
                    call: Call<MovieCastResponse>,
                    response: Response<MovieCastResponse>
                ) {

                    val data = response.body()
                    val data1 = data?.cast
                    textView4.isVisible = true


                    //  rView.layoutManager =
                    //     GridLayoutManager(this@MainActivity,2,RecyclerView.VERTICAL,false)

                    rView20.layoutManager =
                        LinearLayoutManager(this@ActivitySecond, RecyclerView.HORIZONTAL, false)
                    rView20.adapter = data1?.let {
                        moviecastdapter(
                            this@ActivitySecond,
                            it,
                            false
                        )
                    }


                }
            })
        }

        if (id != null) {
            service.getsimilar(id, api_key).enqueue(object : Callback<MovieResponse> {
                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    Log.d("MoviesDagger", t.toString())
                }


                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {

                    val data = response.body()
                    val data1 = data?.results
                    textView5.isVisible = true


                    //  rView.layoutManager =
                    //     GridLayoutManager(this@MainActivity,2,RecyclerView.VERTICAL,false)

                    rView21.layoutManager =
                        LinearLayoutManager(this@ActivitySecond, RecyclerView.HORIZONTAL, false)
                    rView21.adapter = data1?.let {
                        movieadapter(
                            this@ActivitySecond,
                            it,
                            false
                        )
                    }


                }
            })
        }


        if (id != null) {
            service.getvideos(id, api_key).enqueue(object : Callback<VideoResponse> {
                override fun onFailure(call: Call<VideoResponse>, t: Throwable) {
                    Log.d("MoviesDagger", t.toString())
                }


                override fun onResponse(
                    call: Call<VideoResponse>,
                    response: Response<VideoResponse>
                ) {

                    val data = response.body()
                    val data1 = data?.results
                    textView6.isVisible = true

                    //  rView.layoutManager =
                    //     GridLayoutManager(this@MainActivity,2,RecyclerView.VERTICAL,false)

                    rView22.layoutManager =
                        LinearLayoutManager(this@ActivitySecond, RecyclerView.HORIZONTAL, false)
                    rView22.adapter = data1?.let {
                        VideoAdapter(
                            this@ActivitySecond,
                            it,
                            false
                        )
                    }


                }
            })
        }


    }


}
