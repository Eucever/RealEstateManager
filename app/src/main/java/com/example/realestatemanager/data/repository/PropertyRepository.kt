package com.example.realestatemanager.data.repository

import androidx.annotation.WorkerThread
import com.example.realestatemanager.data.dao.PropertyDAO
import com.example.realestatemanager.domain.model.Property
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.threeten.bp.Instant
import javax.inject.Inject

class PropertyRepository @Inject constructor(private val propertyDAO : PropertyDAO ) {
    @WorkerThread
    suspend fun insert(property: Property): Result<Long>{
        return try {
            Result.success(propertyDAO.insert(property.toDTO()))
        }catch (e: Exception) {
            Result.failure(e)
        }
    }

    @WorkerThread
    suspend fun insert(properties: List<Property>): Result<List<Long>>{
        return try {
            Result.success(propertyDAO.insert(properties.map { it.toDTO() }))
        }catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getById(id : Long) : Flow<Property?> {
        return propertyDAO.getById(id).map { it?.toModel() }
    }

    fun getAll(): Flow<List<Property>> {
        return propertyDAO.getAll().map { list -> list.map { it.toModel() } }
    }

    @WorkerThread
    suspend fun delete(property: Property){
        propertyDAO.delete(property.toDTO())
    }

    @WorkerThread
    suspend fun deleteById(id : Long){
        propertyDAO.deleteById(id)
    }

    @WorkerThread
    suspend fun update(property: Property){
        propertyDAO.update(property.toDTO())
    }

    @WorkerThread
    suspend fun search(type: Long?,
                       minPrice: Double?,
                       maxPrice: Double?,
                       minSurface: Double?,
                       maxSurface: Double?,
                       minNbRooms: Int?,
                       maxNbRooms: Int?,
                       isAvailable: Boolean?,
                       commodities: List<Long>?,
                       dateSaleStart : Instant?,
                       dateSaleEnd: Instant?):List<Property> {

        return propertyDAO.search(type,
            minPrice,
            maxPrice,
            minSurface,
            maxSurface,
            minNbRooms,
            maxNbRooms,
            isAvailable,
            dateSaleStart?.toEpochMilli(),
            dateSaleEnd?.toEpochMilli()

            /*commodities*/)
            .filter { property ->
                if (commodities.isNullOrEmpty()) {
                    true
                } else {
                    property.commoditiesIds.any { commodities.contains(it) }
                }
            }
            .map { it.toModel() }
    }

}