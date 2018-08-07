package com.example.kamil.reposapp.api

import com.example.kamil.reposapp.model.BBResponse
import com.example.kamil.reposapp.model.GHResponse
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by Kamil on 06.08.2018.
 */
interface GHApi {

    @GET("repositories")
    fun getGHRepos() : Observable<List<GHResponse>>

}