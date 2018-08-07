package com.example.kamil.reposapp.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.kamil.reposapp.R
import com.example.kamil.reposapp.model.Item

/**
 * Created by Kamil on 06.08.2018.
 */

 class ResultsAdapter : RecyclerView.Adapter<ResultsAdapter.CustomViewHolder>() {

    var items: MutableList<Item?> = mutableListOf()
    val GITHUB_REPO = 1
    val TYPE_REPO = 2

    override fun getItemCount(): Int {
        return items.size;
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CustomViewHolder {
        when (viewType) {
            GITHUB_REPO -> {
                val holder = CustomViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_repo, parent, false))
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
        holder.tvRepoName?.setText(items[position]?.repoName)
        holder.tvOwnerName?.setText(items[position]?.ownerName)
    }

    override fun getItemViewType(position: Int): Int {
        if (items[position]?.isGitHubRepo ?: true) {
            return GITHUB_REPO
        } else
            return TYPE_REPO
    }

    inner class CustomViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val llContainer = v.findViewById<LinearLayout>(R.id.llContainer)
        val tvRepoName = v.findViewById<TextView>(R.id.tvRepoName)
        val tvOwnerName = v.findViewById<TextView>(R.id.tvOwnerName)
        val ivAvatar = v.findViewById<ImageView>(R.id.ivAvatar)

    }
}