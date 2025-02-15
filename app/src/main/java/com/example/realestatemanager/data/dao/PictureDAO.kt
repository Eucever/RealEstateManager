package com.example.realestatemanager.data.dao

import androidx.room.*
import com.example.realestatemanager.data.entity.PictureDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface PictureDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(picture: PictureDTO):Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pictures: List<PictureDTO>):List<Long>

    @Delete
    suspend fun delete(picture: PictureDTO)

    @Query("DELETE FROM picture")
    suspend fun deleteAll()

    @Query("DELETE FROM picture WHERE id=:id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM picture WHERE property_id NOT IN (SELECT id FROM property)")
    suspend fun deleteUnused()

    @Query("DELETE FROM picture WHERE property_id = :propertyId")
    suspend fun deleteByPropertyId(propertyId: Long)

    @Update
    suspend fun update(picture: PictureDTO)

    @Query("SELECT * FROM picture")
    fun getAll(): Flow<List<PictureDTO>>

    @Query("SELECT * FROM picture WHERE id=:id")
    fun getById(id:Long): Flow<PictureDTO?>




}