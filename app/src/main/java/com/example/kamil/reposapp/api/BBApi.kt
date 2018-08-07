package com.example.kamil.reposapp.api

import com.example.kamil.reposapp.model.BBResponse
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by Kamil on 06.08.2018.
 */
interface BBApi {

    @GET("2.0/repositories?fields=values.name,values.owner,values.description")
    fun getBBRepos() : Observable<BBResponse>

}