package com.example.rxjavamvvm.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rxjavamvvm.model.BookListModel
import com.example.rxjavamvvm.network.RetroInstance
import com.example.rxjavamvvm.network.RetroService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel: ViewModel() {
    private val bookList: MutableLiveData<BookListModel> = MutableLiveData()

    fun getBookListObserver(): MutableLiveData<BookListModel> {
        return bookList
    }
    fun makeApiCall(query: String) {
        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
        retroInstance.getBookListFromApi(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getBookListObserverRx())
    }
    private fun getBookListObserverRx(): Observer<BookListModel> {
        return object : Observer<BookListModel> {
            override fun onSubscribe(d: Disposable) {}

            override fun onNext(t: BookListModel) {
                bookList.postValue(t)
            }

            override fun onError(e: Throwable) {
                bookList.postValue(null)
                Log.d("@@@@@","${e.message}")
            }

            override fun onComplete() {}
        }
    }
}