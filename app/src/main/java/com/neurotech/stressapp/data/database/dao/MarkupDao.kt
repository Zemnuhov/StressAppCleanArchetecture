package com.neurotech.stressapp.data.database.dao

import androidx.room.*
import com.neurotech.stressapp.data.database.entity.MarkupEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface MarkupDao {
    @Update
    fun updateMarkup(vararg markup: MarkupEntity)

    @Insert
    fun insertMarkup(vararg markup: MarkupEntity)

    @Delete
    fun deleteMarkup(vararg markup: MarkupEntity)

    @Query("SELECT * from MarkupEntity")
    fun getMarkupList(): Flow<List<MarkupEntity>>

}