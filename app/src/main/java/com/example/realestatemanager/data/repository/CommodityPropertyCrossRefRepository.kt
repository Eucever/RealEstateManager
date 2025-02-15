package com.example.realestatemanager.data.repository

import androidx.annotation.WorkerThread
import com.example.realestatemanager.data.dao.CommodityPropertyCrossRefDAO
import com.example.realestatemanager.data.entity.CommodityPropertyCrossRefDTO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CommodityPropertyCrossRefRepository @Inject constructor(private val commodityPropertyCrossRefDAO: CommodityPropertyCrossRefDAO) {

    @WorkerThread
    suspend fun insert(commodityPropertyCrossRef: CommodityPropertyCrossRefDTO): Result<Long>{
        return try {
            Result.success(commodityPropertyCrossRefDAO.insert(commodityPropertyCrossRef))
        }catch (e: Exception) {
            Result.failure(e)
        }
    }

    @WorkerThread
    suspend fun insert(commodityPropertyCrossRefs: List<CommodityPropertyCrossRefDTO> ): Result<List<Long>>{
        return try {
            Result.success(commodityPropertyCrossRefDAO.insert(commodityPropertyCrossRefs))
        }catch (e: Exception) {
            Result.failure(e)
        }
    }

    @WorkerThread
    suspend fun delete(commodityPropertyCrossRef: CommodityPropertyCrossRefDTO){
        commodityPropertyCrossRefDAO.delete(commodityPropertyCrossRef)
    }

    @WorkerThread
    suspend fun delete(commodityId : Long, propertyId: Long){
        commodityPropertyCrossRefDAO.delete(commodityId, propertyId)
    }

    @WorkerThread
    suspend fun deleteByCommodityId(commodityId: Long){
        commodityPropertyCrossRefDAO.deleteByCommodityId(commodityId)
    }

    @WorkerThread
    suspend fun deleteByPropertyId(propertyId: Long){
        commodityPropertyCrossRefDAO.deleteByPropertyId(propertyId)
    }

    fun getByCommodityId(commodityId: Long): Flow<List<CommodityPropertyCrossRefDTO>>{
        return commodityPropertyCrossRefDAO.getByCommodityId(commodityId)
    }

    fun getByPropertyId(propertyId: Long): Flow<List<CommodityPropertyCrossRefDTO>>{
        return commodityPropertyCrossRefDAO.getByPropertyId(propertyId)
    }

    fun getById(commodityId: Long, propertyId: Long): Flow<CommodityPropertyCrossRefDTO>{
        return commodityPropertyCrossRefDAO.getById(commodityId, propertyId)
    }

}