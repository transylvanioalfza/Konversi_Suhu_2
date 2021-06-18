package org.d3if4118.assessment2.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "suhutable")
data class Suhu(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    @ColumnInfo(name = "suhu")
    var suhu: Double,
    @ColumnInfo(name = "jenis_suhu")
    var jenisSuhu: String,
) : Serializable