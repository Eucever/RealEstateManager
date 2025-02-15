package com.example.realestatemanager.data.repository

import androidx.annotation.WorkerThread
import com.example.realestatemanager.data.dao.CommodityDAO
import com.example.realestatemanager.domain.model.Commodity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CommodityRepository @Inject constructor(private val commodityDAO: CommodityDAO) {
    @WorkerThread
    suspend fun insert(commodity: Commodity): Long{
        return commodityDAO.insert(commodity.toDTO())
    }

    @WorkerThread
    suspend fun insert(commodities: List<Commodity>): List<Long>{
        return commodityDAO.insert(commodities.map { it.toDTO() })
    }


    @WorkerThread
    suspend fun insertAsResult(commodity: Commodity): Result<Long>{
        return try {
            Result.success(commodityDAO.insert(commodity.toDTO()))
        }catch (e: Exception) {
            Result.failure(e)
        }
    }

    @WorkerThread
    suspend fun insertAsResult(commodities: List<Commodity>): Result<List<Long>>{
        return try {
            Result.success(commodityDAO.insert(commodities.map { it.toDTO() }))
        }catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getById(id : Long) : Flow<Commodity?> {
        return commodityDAO.getByIdRT(id).map { it?.toModel() }
    }


    fun getAll(): Flow<List<Commodity>> {
        return commodityDAO.getAllRT().map { list -> list.map { it.toModel() } }
    }

    @WorkerThread
    suspend fun getAllAsResult(): Result<List<Commodity>> {
        return try {
            Result.success(commodityDAO.getAll().map { it.toModel() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    @WorkerThread
    suspend fun deleteAll(){
        commodityDAO.deleteAll()
    }

    @WorkerThread
    suspend fun delete(commodity: Commodity){
        commodityDAO.delete(commodity.toDTO())
    }

    @WorkerThread
    suspend fun deleteById(id : Long){
        commodityDAO.deleteById(id)
    }

    @WorkerThread
    suspend fun update(commodity: Commodity): Int{
       return commodityDAO.update(commodity.toDTO())
    }
}