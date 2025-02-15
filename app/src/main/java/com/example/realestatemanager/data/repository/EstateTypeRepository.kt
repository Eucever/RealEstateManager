package com.example.realestatemanager.data.repository

import androidx.annotation.WorkerThread
import com.example.realestatemanager.data.dao.EstateTypeDAO
import com.example.realestatemanager.domain.model.EstateType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EstateTypeRepository @Inject constructor(private val estateTypeDAO: EstateTypeDAO) {

    @WorkerThread
    suspend fun insert(estateType: EstateType): Result<Long>{
            return try {
                Result.success(estateTypeDAO.insert(estateType.toDTO()))
            }catch (e: Exception) {
                Result.failure(e)
            }
        }

    @WorkerThread
    suspend fun insert(estateTypes: List<EstateType>): Result<List<Long>>{
        return try {
                Result.success(estateTypeDAO.insert(estateTypes.map { it.toDTO() }))
        }catch (e: Exception) {
                Result.failure(e)
        }
    }

    fun getById(id : Long) : Flow<EstateType> {
            return estateTypeDAO.getByIdRT(id).map { it.toModel() }
    }

    fun getAll(): Flow<List<EstateType>> {
            return estateTypeDAO.getAllRT().map { list -> list.map { it.toModel() } }
    }

    @WorkerThread
    suspend fun getAllAsResult(): Result<List<EstateType>> {
        return try {
            Result.success(estateTypeDAO.getAll().map { it.toModel() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    @WorkerThread
    suspend fun deleteAll(){
            estateTypeDAO.deleteAll()
    }

    @WorkerThread
    suspend fun delete(estateType: EstateType){
            estateTypeDAO.delete(estateType.toDTO())
    }

    @WorkerThread
    suspend fun deleteById(id : Long){
            estateTypeDAO.deleteById(id)
    }

    @WorkerThread
    suspend fun update(estateType: EstateType){
            estateTypeDAO.update(estateType.toDTO())
    }
}