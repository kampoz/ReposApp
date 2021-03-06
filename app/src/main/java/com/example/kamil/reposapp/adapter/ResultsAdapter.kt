package com.example.kamil.reposapp.adapter

import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.kamil.reposapp.R
import com.example.kamil.reposapp.RepoFragment
import com.example.kamil.reposapp.model.Item
import com.squareup.picasso.Picasso

/**
 * Created by Kamil on 06.08.2018.
 */

class ResultsAdapter : RecyclerView.Adapter<ResultsAdapter.CustomViewHolder>() {

    var items: MutableList<Item?> = mutableListOf()
    val TYPE_GH = 1
    val TYPE_BB = 2
    var listener: AdapterListener? = null


    override fun getItemCount(): Int {
        return items.size;
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CustomViewHolder {
        when (viewType) {
            TYPE_GH -> {
                val holder = CustomViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_repo, parent, false))
                holder.llContainer.setBackgroundColor(ContextCompat.getColor(parent?.context, R.color.yellow))


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

        Picasso.get()
                .load(item?.avatarUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.ivAvatar);

        holder.llContainer.setOnClickListener { v ->
            listener?.addFragmentToContainer(RepoFragment().newInstance(reponame = item?.repoName, userLogin = item?.ownerName,
                    description = item?.desc, avatarUrl = item?.avatarUrl))
        }
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

interface AdapterListener {
    fun addFragmentToContainer(fragment: Fragment)
}