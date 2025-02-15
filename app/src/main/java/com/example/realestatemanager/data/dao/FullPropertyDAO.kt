package com.example.realestatemanager.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.realestatemanager.data.projection.FullPropertyProjection
import kotlinx.coroutines.flow.Flow

@Dao
interface FullPropertyDAO {
    @Transaction
    @Query("SELECT * FROM property WHERE id = :id")
    fun getByIdRT(id: Long): Flow<FullPropertyProjection?>

    @Transaction
    @Query("SELECT * FROM property")
    fun getAllRT(): Flow<List<FullPropertyProjection>>

    /** SELECT SUSPENDED **/
    @Transaction
    @Query("SELECT * FROM property WHERE id = :id")
    suspend fun getById(id: Long): FullPropertyProjection?

    @Transaction
    @Query("SELECT * FROM property")
    suspend fun getAll(): List<FullPropertyProjection>
}