package org.d3if4118.assessment2.ui.home

import android.app.Application
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.*
import org.d3if4118.assessment2.database.Suhu
import org.d3if4118.assessment2.database.SuhuDao


class HomeProsesViewModel(private val database: SuhuDao) :
    ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    val listSuhu = database.getAllSuhu()


    val liveList = Transformations.map(listSuhu) { list ->
        return@map list
    }

    fun onClickSimpanData(
        suhu: Double,
        jenisSuhu:String
    ) {
        uiScope.launch {
            val listSuhu =
                Suhu(0, suhu,jenisSuhu)
            insert(listSuhu)
        }
    }

    private suspend fun insert(suhu: Suhu) {
        withContext(Dispatchers.IO) {
            database.insert(suhu)
        }
    }

    fun onClickHapusSemua() {
        uiScope.launch {
            hapusSemua()
        }
    }

    private suspend fun hapusSemua() {
        withContext(Dispatchers.IO) {
            database.hapusSemua()
        }
    }

    fun onClickUpdate(suhu: Suhu) {
        uiScope.launch {
            update(suhu)
        }
    }

    private suspend fun update(suhu: Suhu) {
        withContext(Dispatchers.IO) {
            database.update(suhu)
        }
    }

    fun onClickHapus(id: Long) {
        uiScope.launch {
            delete(id)
        }
    }

    private suspend fun delete(id: Long) {
        withContext(Dispatchers.IO) {
            database.hapus(id)
        }
    }
}

class HomeDataViewModelFactory(
    private val dataSource: SuhuDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeProsesViewModel::class.java)) {
            return HomeProsesViewModel(
                dataSource
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}