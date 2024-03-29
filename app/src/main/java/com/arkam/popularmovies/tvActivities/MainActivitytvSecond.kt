package com.arkam.popularmovies.tvActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arkam.popularmovies.model.*
import com.arkam.popularmovies.network.PopInterface
import com.arkam.popularmovies.R
import com.arkam.popularmovies.tvAdapters.TvAdapterCommon
import com.arkam.popularmovies.tvAdapters.tvcastdapter
import com.arkam.popularmovies.tvAdapters.VideoAdapterTv
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_4.*

import kotlinx.android.synthetic.main.activity_second.imageview2
import kotlinx.android.synthetic.main.activity_second.iview
import kotlinx.android.synthetic.main.activity_second.tv1
import kotlinx.android.synthetic.main.activity_second.tv3
import kotlinx.android.synthetic.main.activity_second.tv7
import kotlinx.android.synthetic.main.activity_second.tvoverview
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivitytvSecond : AppCompatActivity() {
    val api_key: String = "0e03d86efe00ea1a1e1dd7d2a4717ba1"
    var maxLimit: Int = 996
    val retrofit = Retrofit.Builder().baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val baseURL = "https://image.tmdb.org/t/p/w780/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_4)
        val id = intent.getStringExtra("id").toInt()

        textView41.isVisible = false
        textView51.isVisible = false
        textView61.isVisible = false

        val type = intent.getStringExtra("type")
        val service = retrofit.create(PopInterface::class.java)

        service.gettv(id, api_key).enqueue(object : Callback<tv> {
            override fun onFailure(call: Call<tv>, t: Throwable) {
                Log.d("MoviesDagger", t.toString())
            }


            override fun onResponse(call: Call<tv>, response: Response<tv>) {

                val data = response.body()



                if (data != null) {
                    Picasso.get().load(baseURL + data.backdrop_path).resize(413, 200).into(iview)
                    Picasso.get().load(baseURL + data.poster_path).into(imageview2)
                }
                tv7.text = data?.original_name
                tv1.text = "Release Date    " + data?.release_date
                tv3.text = data?.vote_average + "/10"
                tvoverview.text = data?.overview


            }
        })

        service.gettvcast(id, api_key).enqueue(object : Callback<tvcastresponse> {
            override fun onFailure(call: Call<tvcastresponse>, t: Throwable) {
                Log.d("MoviesDagger", t.toString())
            }


            override fun onResponse(
                call: Call<tvcastresponse>,
                response: Response<tvcastresponse>
            ) {

                val data = response.body()
                val data1 = data?.cast

                textView41.isVisible = true

                //  rView.layoutManager =
                //     GridLayoutManager(this@MainActivity,2,RecyclerView.VERTICAL,false)

                rViewcasttv.layoutManager =
                    LinearLayoutManager(this@MainActivitytvSecond, RecyclerView.HORIZONTAL, false)
                rViewcasttv.adapter = data1?.let {
                    tvcastdapter(
                        this@MainActivitytvSecond,
                        it,
                        false
                    )
                }


            }
        })

        service.getsimilartv(id, api_key).enqueue(object : Callback<tvresponse> {
            override fun onFailure(call: Call<tvresponse>, t: Throwable) {
                Log.d("MoviesDagger", t.toString())
            }


            override fun onResponse(call: Call<tvresponse>, response: Response<tvresponse>) {

                val data = response.body()
                val data1 = data?.results
                textView51.isVisible = true


                //  rView.layoutManager =
                //     GridLayoutManager(this@MainActivity,2,RecyclerView.VERTICAL,false)

                rViewsimilartv.layoutManager =
                    LinearLayoutManager(this@MainActivitytvSecond, RecyclerView.HORIZONTAL, false)
                rViewsimilartv.adapter = data1?.let {
                    TvAdapterCommon(
                        this@MainActivitytvSecond,
                        it,
                        false
                    )
                }


            }
        })

        service.getvideostv(id, api_key).enqueue(object : Callback<videoresponsetv> {
            override fun onFailure(call: Call<videoresponsetv>, t: Throwable) {
                Log.d("MoviesDagger", t.toString())
            }


            override fun onResponse(
                call: Call<videoresponsetv>,
                response: Response<videoresponsetv>
            ) {

                val data = response.body()
                val data1 = data?.results

                textView61.isVisible = false
                //  rView.layoutManager =
                //     GridLayoutManager(this@MainActivity,2,RecyclerView.VERTICAL,false)

                rViewvideotv.layoutManager =
                    LinearLayoutManager(this@MainActivitytvSecond, RecyclerView.HORIZONTAL, false)
                rViewvideotv.adapter = data1?.let {
                    VideoAdapterTv(
                        this@MainActivitytvSecond,
                        it,
                        false
                    )
                }


            }
        })

    }
}
