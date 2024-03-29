package com.arkam.popularmovies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.arkam.popularmovies.model.MovieSearch
import com.arkam.popularmovies.movieActivites.MainActivity
import com.arkam.popularmovies.network.PopInterface
import com.arkam.popularmovies.peopleActivities.MainActivityPeople
import com.arkam.popularmovies.tvActivities.MainActivitytv
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_7.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivityFavourite : AppCompatActivity() {

    val api_key: String = "0e03d86efe00ea1a1e1dd7d2a4717ba1"
    var maxLimit: Int = 996
    val retrofit = Retrofit.Builder().baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val baseURL = "https://image.tmdb.org/t/p/w780/"
    val service = retrofit.create(PopInterface::class.java)
    var favList: ArrayList<MovieSearch> = arrayListOf<MovieSearch>()
    var check: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_7)
        val naview = findViewById<View>(R.id.nav) as BottomNavigationView
        var menu = naview.menu
        var menuItem = menu.getItem(3)
        menuItem.isChecked = true

        favList.clear()

        naview.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.movies -> {
                    val intent1 = Intent(this, MainActivity::class.java)
                    startActivity(intent1)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.tv -> {
                    val intent2 = Intent(this, MainActivitytv::class.java)
                    startActivity(intent2)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.person -> {
                    val intent3 = Intent(this, MainActivityPeople::class.java)
                    startActivity(intent3)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.FAV -> {

                    return@setOnNavigationItemSelectedListener true
                }

                else -> return@setOnNavigationItemSelectedListener true


            }

        }


        val db: FavouriteDatabase by lazy {
            Room.databaseBuilder(
                this,
                FavouriteDatabase::class.java,
                "Fav.db"
            ).allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }

        //var db=FavouriteDatabase.getfavouriteDatabase(this)
        var a = db.FavDao().getallfav()
        Log.d("AMITOZ", a.size.toString())

        for (i in 0..a.lastIndex) {
            Log.d("myCHECK", "LOOP")
//            var id = a[1].movie_id.toInt()
//            var path = a[1].path
//            var a=imagView0
//            Log.d("sss",a.toString())
//            Picasso.get().load(baseURL + path).resize(150, 270).into(a)


            service.getmovies(a[i].movie_id.toInt(), api_key)
                .enqueue(object : Callback<MovieSearch> {
                    override fun onFailure(call: Call<MovieSearch>, t: Throwable) {
                        Log.d("MoviesDagger", t.toString())
                    }


                    override fun onResponse(
                        call: Call<MovieSearch>,
                        response: Response<MovieSearch>
                    ) {

                        val data = response.body()
                        Log.d("gggg", data!!.original_title)
                        favList.add(data)
                        Log.d("myCHECK", "ADDED")

                        if (i == a.lastIndex) {
                            Log.d("myCHECK", "${favList.size}")
                            if (favList.size != 0) {
                                check = false
                            }

                            rvfav.layoutManager =
                                GridLayoutManager(
                                    this@MainActivityFavourite,
                                    2,
                                    RecyclerView.VERTICAL,
                                    false
                                )
                            rvfav.adapter =
                                favouriteAdapter(
                                    this@MainActivityFavourite,
                                    favList,
                                    check
                                )
                            rvfav.adapter!!.notifyDataSetChanged()
                        }


                        //  rView.layoutManager =
                        //     GridLayoutManager(this@MainActivity,2,RecyclerView.VERTICAL,false)

//                    rvfav.layoutManager =
//                        GridLayoutManager(this@MainActivityFavourite,2, RecyclerView.VERTICAL,false)
//                    rvfav.paddingTop
//                    rvfav.adapter = data?.let {
//                        favouriteAdapter(
//                            this@MainActivityFavourite,
//                            it,
//                            false
//                        )
//                    }


                    }
                })
        }
        favList.clear()

    }

    override fun onBackPressed() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finishAffinity()
    }

}
