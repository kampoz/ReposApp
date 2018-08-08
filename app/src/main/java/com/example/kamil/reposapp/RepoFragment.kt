package com.example.kamil.reposapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

/**
 * Created by Kamil on 2018-08-08.
 */

class RepoFragment : Fragment() {

    var userLogin: String? = null
    var repoName: String? = null
    var desc: String? = null
    var avatarUrl: String? = null

    fun newInstance(userLogin: String?, reponame: String?, description: String? = "No info", avatarUrl: String?): RepoFragment {
        val fragment = RepoFragment()
        fragment.userLogin = userLogin
        fragment.repoName = repoName
        fragment.desc = description
        fragment.avatarUrl = avatarUrl
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_repo, container, false)

        val tvRepoName = view.findViewById<TextView>(R.id.tv_repo_name)
        val tvOwnerName = view.findViewById<TextView>(R.id.tv_owner_name)
        val tvDesc = view.findViewById<TextView>(R.id.tv_description)
        val ivAvatar = view.findViewById<ImageView>(R.id.iv_avatar)

        tvRepoName.setText(repoName)
        tvOwnerName.setText(userLogin)
        tvDesc.setText(desc)
        Picasso.get()
                .load(avatarUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(ivAvatar);

        return view


    }
}