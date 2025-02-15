package com.example.realestatemanager.data.repository

import androidx.annotation.WorkerThread
import com.example.realestatemanager.data.dao.LocationDAO
import com.example.realestatemanager.domain.model.Location
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocationRepository @Inject constructor(private val locationDAO: LocationDAO) {
    @WorkerThread
    suspend fun insert(location: Location): Long {
        return locationDAO.insert(location.toDTO())
    }

    @WorkerThread
    suspend fun insert(locations: List<Location>): List<Long> {
        return locationDAO.insert(locations.map { it.toDTO() })
    }


    @WorkerThread
    suspend fun insertAsResult(location: Location): Result<Long>{
        return try {
            Result.success(locationDAO.insert(location.toDTO()))
        }catch (e: Exception) {
            Result.failure(e)
        }
    }

    @WorkerThread
    suspend fun insertAsResult(locations: List<Location>): Result<List<Long>>{
        return try {
            Result.success(locationDAO.insert(locations.map { it.toDTO() }))
        }catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getById(id : Long) : Flow<Location?> {
        return locationDAO.getById(id).map { it?.toModel() }
    }

    fun getAll(): Flow<List<Location>> {
        return locationDAO.getAll().map { list -> list.map { it.toModel() }}
    }

    @WorkerThread
    suspend fun deleteAll(){
        locationDAO.deleteAll()
    }

    @WorkerThread
    suspend fun delete(location: Location){
        locationDAO.delete(location.toDTO())
    }

    @WorkerThread
    suspend fun deleteById(id : Long){
        locationDAO.deleteById(id)
    }

    @WorkerThread
    suspend fun update(location: Location): Int{
        return locationDAO.update(location.toDTO())
    }

    fun search(street: String, streetNumber: Int?, postalCode: String, city: String, country: String): Flow<Long?> {
        return locationDAO.search(street, streetNumber, postalCode, city, country)
    }

}