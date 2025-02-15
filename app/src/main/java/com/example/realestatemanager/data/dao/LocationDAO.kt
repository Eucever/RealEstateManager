package com.example.realestatemanager.data.dao

import androidx.room.*
import com.example.realestatemanager.data.entity.LocationDTO
import kotlinx.coroutines.flow.Flow


@Dao
interface LocationDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(location: LocationDTO) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(locations : List<LocationDTO>): List<Long>

    @Query("DELETE FROM location")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(place: LocationDTO)

    @Query("DELETE FROM location WHERE id=:id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM location WHERE id NOT IN (SELECT location_id FROM property)")
    suspend fun deleteUnused()

    @Update
    suspend fun update(location : LocationDTO): Int

    @Query("SELECT id FROM location WHERE street = :street AND (street_number IS NULL OR street_number = :streetNumber) AND postal_code = :postalCode AND city = :city AND country = :country")
    fun search(street: String, streetNumber: Int?, postalCode: String, city: String, country: String): Flow<Long?>

    @Query("SELECT * FROM location")
    fun getAll(): Flow<List<LocationDTO>>

    @Query("SELECT * FROM location WHERE id=:id")
    fun getById(id:Long): Flow<LocationDTO?>
}