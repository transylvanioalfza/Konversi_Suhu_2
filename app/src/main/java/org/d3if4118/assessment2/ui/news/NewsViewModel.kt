package org.d3if4118.assessment2.ui.news

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if4118.assessment2.model.Articles
import org.d3if4118.assessment2.network.ApiService
import org.d3if4118.assessment2.network.ApiStatus
import org.d3if4118.assessment2.utils.Constant.API_KEY


class NewsViewModel : ViewModel() {
    private val newsData = MutableLiveData<List<Articles>>()
    private val status = MutableLiveData<ApiStatus>()

    //data status
    fun getStatus(): LiveData<ApiStatus> = status

    fun getData(): LiveData<List<Articles>> = newsData

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                requestData()
            }
        }
    }

    private suspend fun requestData() {
        try {
            status.postValue(ApiStatus.LOADING)
            val result = ApiService.service.getListNews("id", "health", API_KEY)
            newsData.postValue(result.articles)
            Log.d("REQUEST", "Nama : ${result.articles.size}")

            status.postValue(ApiStatus.SUCCESS)
        } catch (e: Exception) {
            status.postValue(ApiStatus.FAILED)
            Log.d("REQUEST", e.message.toString())
        }
    }
}