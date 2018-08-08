package com.example.kamil.reposapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import com.example.kamil.reposapp.adapter.AdapterListener
import com.example.kamil.reposapp.adapter.ResultsAdapter
import com.example.kamil.reposapp.api.ApiManager
import com.example.kamil.reposapp.model.Item
import java.util.*

class MainActivity : AppCompatActivity(), AdapterListener {

    var adapter = ResultsAdapter();
    var allItems = mutableSetOf<Item?>()
    var apiManager = ApiManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSort = findViewById<Button>(R.id.btn_sort)
        btnSort.setOnClickListener{ v -> sortByName()}

        val rv = findViewById<RecyclerView>(R.id.rv_results)
        rv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        adapter.listener = this
        rv.adapter = adapter

//        searchBBRepos()
//        searchGHRepos(rv)
        loadAllRepos()

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

    fun searchGHRepos(rv: RecyclerView) {
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
            Log.i("ReposApp", "allItems table size " + allItems.size);
            adapter.items.addAll(allItems)
            rv.adapter = adapter
            adapter.notifyDataSetChanged()
        }
        )
    }

    fun loadAllRepos(){
        allItems.clear()
        adapter.items.clear()
        adapter.notifyDataSetChanged()
        apiManager.loadGHRepos().mergeWith(apiManager.loadBBRepos())
                .subscribe({
                    allItems.add(it)
                }, {
                    error -> Log.i("ReposApp", "Error while load repos", error)
                })
    }

    fun sortByName(){
        val list2 = adapter.items.sortedWith(compareBy({it?.repoName}))
        adapter.items.clear()
        adapter.items.addAll( list2)
        adapter.notifyDataSetChanged()
    }

    override fun addFragmentToContainer(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .add(R.id.fl_container, fragment)
                .addToBackStack(null)
                .commit()
    }
}
