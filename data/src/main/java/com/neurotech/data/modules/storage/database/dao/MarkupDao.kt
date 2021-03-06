package com.neurotech.data.modules.storage.database.dao

import androidx.room.*
import com.neurotech.data.modules.storage.database.entity.MarkupEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface MarkupDao {
    @Update
    fun updateMarkup(vararg markup: MarkupEntity)

    @Insert
    fun insertMarkup(vararg markup: MarkupEntity)

    @Delete
    fun deleteMarkup(vararg markup: MarkupEntity)

    @Query("SELECT * from MarkupEntity GROUP BY timeBegin")
    fun getMarkupListFlow(): Flow<List<MarkupEntity>>

    @Query("SELECT * from MarkupEntity WHERE NOT markupName = :markup ")
     fun getMarkupList(vararg markup: String): List<MarkupEntity>

}