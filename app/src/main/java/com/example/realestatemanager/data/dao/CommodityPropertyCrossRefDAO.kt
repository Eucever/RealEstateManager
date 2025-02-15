package com.example.realestatemanager.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.realestatemanager.data.entity.CommodityPropertyCrossRefDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface CommodityPropertyCrossRefDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(commodityPropertyCrossRefDTO: CommodityPropertyCrossRefDTO): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(commodityPropertyCrossRefDTOS : List<CommodityPropertyCrossRefDTO>): List<Long>

    @Delete
    suspend fun delete(commodityPropertyCrossRefDTO: CommodityPropertyCrossRefDTO)

    @Query("DELETE FROM commodity_property WHERE commodity_id=:commodityId AND property_id=:propertyId")
    suspend fun delete(commodityId: Long, propertyId: Long)

    @Query("DELETE FROM commodity_property WHERE commodity_id=:commodityId")
    suspend fun deleteByCommodityId(commodityId: Long)

    @Query("DELETE FROM commodity_property WHERE property_id=:propertyId")
    suspend fun deleteByPropertyId(propertyId: Long)

    @Query("SELECT * FROM commodity_property WHERE commodity_id=:commodityId")
    fun getByCommodityId(commodityId: Long): Flow<List<CommodityPropertyCrossRefDTO>>

    @Query("SELECT * FROM commodity_property WHERE property_id=:propertyId")
    fun getByPropertyId(propertyId: Long): Flow<List<CommodityPropertyCrossRefDTO>>

    @Query("SELECT * FROM commodity_property WHERE commodity_id=:commodityId AND property_id=:propertyId")
    fun getById(commodityId: Long, propertyId: Long): Flow<CommodityPropertyCrossRefDTO>
}