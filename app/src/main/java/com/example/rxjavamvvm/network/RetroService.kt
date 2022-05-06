package com.example.rxjavamvvm.network

import com.example.rxjavamvvm.model.BookListModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RetroService {

    @GET("volumes")
    fun getBookListFromApi(@Query("q") q: String): Observable<BookListModel>
}