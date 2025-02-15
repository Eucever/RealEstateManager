package com.example.realestatemanager.data.repository

import androidx.annotation.WorkerThread
import com.example.realestatemanager.data.dao.PictureDAO
import com.example.realestatemanager.domain.model.Picture
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PictureRepository @Inject constructor(private val pictureDAO : PictureDAO){
    @WorkerThread
    suspend fun insert(picture: Picture, propertyId : Long): Long {
        return pictureDAO.insert(picture.toDTO(propertyId))
    }

    @WorkerThread
    suspend fun insert(pictures: List<Picture>, propertyId : Long): List<Long> {
        return pictureDAO.insert(pictures.map { it.toDTO(propertyId) })
    }



    @WorkerThread
    suspend fun insertAsResult(picture: Picture, propertyId : Long): Result<Long>{
        return try {
            Result.success(pictureDAO.insert(picture.toDTO(propertyId)))
        }catch (e: Exception) {
            Result.failure(e)
        }
    }

    @WorkerThread
    suspend fun insertAsResult(pictures: List<Picture>, propertyId : Long): Result<List<Long>>{
        return try {
            Result.success(pictureDAO.insert(pictures.map { it.toDTO(propertyId) }))
        }catch (e: Exception) {
            Result.failure(e)
        }
    }


    fun getById(id : Long) : Flow<Picture?> {
        return pictureDAO.getById(id).map { it?.toModel() }
    }

    fun getAll(): Flow<List<Picture>> {
        return pictureDAO.getAll().map { list -> list.map { it.toModel() } }
    }

    @WorkerThread
    suspend fun deleteAll(){
        pictureDAO.deleteAll()
    }

    @WorkerThread
    suspend fun delete(picture: Picture, propertyId: Long){
        pictureDAO.delete(picture.toDTO(propertyId))
    }

    @WorkerThread
    suspend fun deleteById(id: Long){
        pictureDAO.deleteById(id)
    }

    @WorkerThread
    suspend fun update(picture : Picture, propertyId: Long){
        pictureDAO.update(picture.toDTO(propertyId))
    }

}