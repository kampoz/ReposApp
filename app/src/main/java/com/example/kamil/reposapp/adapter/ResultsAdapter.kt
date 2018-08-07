package com.example.kamil.reposapp.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.kamil.reposapp.R
import com.example.kamil.reposapp.model.Item

/**
 * Created by Kamil on 06.08.2018.
 */

 class ResultsAdapter : RecyclerView.Adapter<ResultsAdapter.CustomViewHolder>() {

    var items: MutableList<Item?> = mutableListOf()
    val TYPE_GH = 1
    val TYPE_BB = 2

    override fun getItemCount(): Int {
        return items.size;
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CustomViewHolder {
        when (viewType) {
            TYPE_GH -> {
                val holder = CustomViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_repo, parent, false))
                holder.llContainer.setBackgroundColor(ContextCompat.getColor(parent?.context, R.color.yellow ))
                holder.llContainer.setOnClickListener { v ->
//                    run {
//                        Toast.makeText(baseContext, "User clicked!", Toast.LENGTH_LONG).show()
//                        addFragmentToContainer(UserFragment().newInstance(holder.tvName.text.toString()))
//                    }
                }

                return holder
            }
            else -> {
                return CustomViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_repo, parent, false))
            }
        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val item: Item? = items[position]
        holder.tvRepoName?.setText(item?.repoName)
        holder.tvOwnerName?.setText(item?.ownerName)
    }

    override fun getItemViewType(position: Int): Int {
        if (items[position]?.isGH ?: true) {
            return TYPE_GH
        } else
            return TYPE_BB
    }

    inner class CustomViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val llContainer = v.findViewById<LinearLayout>(R.id.llContainer)
        val tvRepoName = v.findViewById<TextView>(R.id.tvRepoName)
        val tvOwnerName = v.findViewById<TextView>(R.id.tvOwnerName)
        val ivAvatar = v.findViewById<ImageView>(R.id.ivAvatar)

    }
}