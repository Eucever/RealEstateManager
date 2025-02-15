package com.example.realestatemanager.data.dao

import androidx.room.*
import com.example.realestatemanager.data.entity.CommodityDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface CommodityDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(commodity : CommodityDTO): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(commodities : List<CommodityDTO>): List<Long>

    @Delete
    suspend fun delete(commodity: CommodityDTO)

    @Query("DELETE FROM commodity")
    suspend fun deleteAll()

    @Query("DELETE FROM commodity WHERE id=:id")
    suspend fun deleteById(id: Long)

    @Update
    suspend fun update(commodity: CommodityDTO): Int

    /**SELECT REAL TIME / FLOW**/
    @Query("SELECT * FROM commodity")
    fun getAllRT(): Flow<List<CommodityDTO>>

    @Query("SELECT * FROM commodity WHERE id=:id")
    fun getByIdRT(id:Long): Flow<CommodityDTO?>

    /**SELECT SUSPENDED**/
    @Query("SELECT * FROM commodity WHERE id = :id")
    suspend fun getById(id: Long): CommodityDTO?

    @Query("SELECT * FROM commodity")
    suspend fun getAll(): List<CommodityDTO>

}