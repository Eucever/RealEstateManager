package com.example.realestatemanager.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.realestatemanager.data.entity.EstateTypeDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface EstateTypeDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(estateType: EstateTypeDTO): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(estateTypes: List<EstateTypeDTO>): List<Long>

    @Delete
    suspend fun delete(estateType : EstateTypeDTO)

    @Query("DELETE FROM estate_type")
    suspend fun deleteAll()

    @Query("DELETE FROM estate_type WHERE id=:id")
    suspend fun deleteById(id: Long)

    @Update
    suspend fun update(estateType : EstateTypeDTO)

    /** SELECT REAL TIME / FLOW **/
    @Query("SELECT * FROM estate_type")
    fun getAllRT(): Flow<List<EstateTypeDTO>>

    @Query("SELECT * FROM estate_type WHERE id=:id")
    fun getByIdRT(id:Long): Flow<EstateTypeDTO>

    /** SELECT SUSPENDED **/
    @Query("SELECT * FROM estate_type")
    suspend fun getAll(): List<EstateTypeDTO>
}