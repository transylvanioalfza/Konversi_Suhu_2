package org.d3if4118.assessment2.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface SuhuDao {

    @Insert
    fun insert(suhu: Suhu)

    @Update
    fun update(suhu: Suhu)

    @Query("SELECT * FROM suhutable")
    fun getAllSuhu(): LiveData<List<Suhu>>

    @Query("DELETE  FROM suhutable ")
    fun hapusSemua()

    @Query("Delete from suhutable where id= :idSuhu")
    fun hapus(idSuhu: Long)

}