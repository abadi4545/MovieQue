package com.arkam.popularmovies.searchActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arkam.popularmovies.network.PopInterface
import com.arkam.popularmovies.R
import com.arkam.popularmovies.model.SearchResponse
import com.arkam.popularmovies.searchAdapters.SearchAdapter
import com.arkam.popularmovies.searchAdapters.SearchAdapterPeople
import com.arkam.popularmovies.searchAdapters.SearchAdapterTv
import kotlinx.android.synthetic.main.activity_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity() {
    val api_key: String = "0e03d86efe00ea1a1e1dd7d2a4717ba1"
    var maxLimit: Int = 996
    val retrofit = Retrofit.Builder().baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(PopInterface::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val query = intent.getStringExtra("text")
        val type = intent.getStringExtra("type")
        progressBarsearch.isVisible = true


        if (type == "people") {


            service.getSearchPeople(api_key, query).enqueue(object : Callback<SearchResponse> {
                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    Log.d("MoviesDagger", t.toString())
                }


                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>
                ) {

                    val data = response.body()
                    val data1 = data?.results
                    progressBarsearch.isVisible = false

                    //  rView.layoutManager =
                    //     GridLayoutManager(this@MainActivity,2,RecyclerView.VERTICAL,false)

                    rViewnew.layoutManager =
                        GridLayoutManager(this@SearchActivity, 2, RecyclerView.VERTICAL, false)
                    rViewnew.adapter = data1?.let {
                        SearchAdapterPeople(
                            this@SearchActivity,
                            it,
                            false
                        )
                    }


                }
            })

        } else
            if (type == "movie") {
                service.getSearchMovie(api_key, query).enqueue(object : Callback<SearchResponse> {
                    override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                        Log.d("MoviesDagger", t.toString())
                    }


                    override fun onResponse(
                        call: Call<SearchResponse>,
                        response: Response<SearchResponse>
                    ) {

                        val data = response.body()
                        val data1 = data?.results
                        progressBarsearch.isVisible = false


                        //  rView.layoutManager =
                        //     GridLayoutManager(this@MainActivity,2,RecyclerView.VERTICAL,false)

                        rViewnew.layoutManager =
                            GridLayoutManager(this@SearchActivity, 2, RecyclerView.VERTICAL, false)
                        rViewnew.adapter = data1?.let {
                            SearchAdapter(
                                this@SearchActivity,
                                it,
                                false
                            )
                        }


                    }
                })


            } else {
                service.getSearchTv(api_key, query).enqueue(object : Callback<SearchResponse> {
                    override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                        Log.d("MoviesDagger", t.toString())
                    }


                    override fun onResponse(
                        call: Call<SearchResponse>,
                        response: Response<SearchResponse>
                    ) {

                        val data = response.body()
                        val data1 = data?.results
                        progressBarsearch.isVisible = false

                        //  rView.layoutManager =
                        //     GridLayoutManager(this@MainActivity,2,RecyclerView.VERTICAL,false)

                        rViewnew.layoutManager =
                            GridLayoutManager(this@SearchActivity, 2, RecyclerView.VERTICAL, false)
                        rViewnew.adapter = data1?.let {
                            SearchAdapterTv(
                                this@SearchActivity,
                                it,
                                false
                            )
                        }


                    }
                })
            }
    }
}
