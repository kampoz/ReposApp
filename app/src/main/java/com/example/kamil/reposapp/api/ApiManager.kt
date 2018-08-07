package com.example.kamil.reposapp.api

import com.example.kamil.reposapp.model.GHResponse
import com.example.kamil.reposapp.model.Item
import com.example.kamil.reposapp.model.Value
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Kamil on 06.08.2018.
 */
class ApiManager {

    val bbApi: BBApi
        get() = buildBBRetrofit().create<BBApi>(BBApi::class.java!!)

    val ghApi: GHApi
        get() = buildGHRetrofit().create<GHApi>(GHApi::class.java)

    private fun buildBBRetrofit(): Retrofit {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()

        val httpLoggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)

        return Retrofit.Builder()
                .baseUrl(BASE_URL_BITBUCKET)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClientBuilder.build())
                .build()
    }


    private fun buildGHRetrofit(): Retrofit {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()

        val httpLoggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)

        return Retrofit.Builder()
                .baseUrl(BASE_URL_GITHUB)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClientBuilder.build())
                .build()
    }

//    private fun buildRetrofitForBitBucket(): Retrofit {
//        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
//
//        val httpLoggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
//        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
//        okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)
//
//        return Retrofit.Builder()
//                .baseUrl(BASE_URL_BITBUCKET)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .client(okHttpClientBuilder.build())
//                .build()
//    }


//    fun loadBBRepos() : Observable<Item> {
//        return bbApi.getBBRepos()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .map { resp -> resp.values }
//                .flatMapIterable { values -> values
//                        .map{}
//
////                .map { ApiManager::getItemObservableBB }
//    }
//    }

    fun loadBBRepos(): Observable<Item> {
        return bbApi.getBBRepos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { resp -> resp.values }
                .flatMapIterable { v -> v }
                .map { v -> getItemObservableBB(v) }
                .flatMap { v -> v }
    }

    fun loadGHRepos(): Observable<Item> {
        return ghApi.getGHRepos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapIterable { r -> r }
                .map{ r -> getItemObservableFromGH(r)}
                .flatMap { r -> r }
    }

    fun getItemObservableBB(value: Value?): Observable<Item> {
        val item = Item()
        item.ownerName = value?.owner?.username
        item.repoName = value?.name
        item.desc = value?.description
        //todo uzupełnic avatar BB
        item.isGH = false

        return Observable.just(item)
    }

    fun getItemObservableFromGH(ghResponse: GHResponse?): Observable<Item> {
        val item = Item()
        item.ownerName = ghResponse?.owner?.login
        item.repoName = ghResponse?.name
        item.avatarUrl = ghResponse?.owner?.avatar_url
        item.desc = ghResponse?.description
        item.isGH = true
        return Observable.just(item)
    }

    companion object {
        val BASE_URL_GITHUB = "https://api.github.com/"
        val BASE_URL_BITBUCKET = "https://api.bitbucket.org/"
    }
}