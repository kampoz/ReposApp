package com.example.kamil.reposapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.LinearLayout
import com.example.kamil.reposapp.adapter.ResultsAdapter
import com.example.kamil.reposapp.api.ApiManager
import com.example.kamil.reposapp.model.Item

class MainActivity : AppCompatActivity() {

    var adapter = ResultsAdapter();
    var allItems = mutableSetOf<Item?>()
    var apiManager = ApiManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rv = findViewById<RecyclerView>(R.id.rvResults)
        rv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rv.adapter = adapter

//        searchBBRepos()
        searchGHRepos()
        Log.d("tag", "cos tam")

    }

    fun searchBBRepos() {
        allItems.clear()
        adapter.items.clear()
        adapter.notifyDataSetChanged()
        apiManager.loadBBRepos()?.subscribe(
                {
                    allItems.add(it)
                }, { error ->
            Log.i("ReposApp", "Error while search repos on BB", error)
        }
        )
    }

    fun searchGHRepos() {
        allItems.clear()
        adapter.items.clear()
        adapter.notifyDataSetChanged()
        apiManager.loadGHRepos()?.subscribe(
                {
                    allItems.add(it)
                },
                { error ->
            Log.i("ReposApp", "Error while search repos on GH", error)
        }, {
            Log.i("ReposApp", "allItems table size "+allItems.size);
        }
        )
    }
}
