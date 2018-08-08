package com.example.kamil.reposapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by Kamil on 2018-08-08.
 */

class RepoFragment : Fragment() {

    fun newInstance(userLogin: String): RepoFragment {
        val fragment = RepoFragment()
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_repo, container, false)

        return view


    }
}