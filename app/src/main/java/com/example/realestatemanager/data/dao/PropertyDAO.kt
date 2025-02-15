package com.example.realestatemanager.data.dao

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.realestatemanager.data.entity.PropertyDTO
import com.example.realestatemanager.data.flatten.PropertyListItemFlatten
import kotlinx.coroutines.flow.Flow


@Dao
interface PropertyDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(property: PropertyDTO): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(properties: List<PropertyDTO>): List<Long>

    @Update
    suspend fun update(property: PropertyDTO)

    @Query("DELETE FROM property")
    suspend fun deleteAll()

    @Query("DELETE FROM property WHERE id=:id")
    suspend fun deleteById(id: Long)

    @Delete
    suspend fun delete(property : PropertyDTO)

    @Query("SELECT * FROM property")
    fun getAll(): Flow<List<PropertyDTO>>

    @Query("SELECT * FROM property WHERE id=:id")
    fun getById(id:Long): Flow<PropertyDTO?>

    @Query("""
        SELECT 
            p.id,
            p.name,
            p.description,
            p.surface,
            p.nb_rooms AS nbRooms,
            p.price,
            p.is_sold AS isSold,
            p.date_of_creation AS dateCreation,
            p.date_of_entry AS dateEntry,
            p.date_of_sale AS dateSale,
            et.name AS type,
            l.street,
            l.postal_code AS postalCode,
            l.city,
            l.country,
            l.longitude,
            l.latitude,
            a.first_name || ' ' || a.last_name AS agentName,
            GROUP_CONCAT(c.type,',') AS commoditiesType,
            GROUP_CONCAT(c.id,',') AS commoditiesIds,
            pi.content AS picture
        FROM property p
        LEFT OUTER JOIN estate_type et ON et.id = p.type_id
        LEFT OUTER JOIN location l ON l.id = p.location_id
        LEFT OUTER JOIN agent a ON a.id = p.agent_id
        LEFT OUTER JOIN commodity_property cp ON cp.property_id = p.id
        LEFT OUTER JOIN commodity c ON c.id = cp.commodity_id
        LEFT OUTER JOIN picture pi ON pi.property_id = p.id AND pi.`order` = 0
        WHERE
            (:type IS NULL OR et.id = :type) AND
            (:minPrice IS NULL OR p.price >= :minPrice) AND
            (:maxPrice IS NULL OR p.price <= :maxPrice) AND
            (:minSurface IS NULL OR p.surface >= :minSurface) AND
            (:maxSurface IS NULL OR p.surface <= :maxSurface) AND
            (:minNbRooms IS NULL OR p.nb_rooms >= :minNbRooms) AND
            (:maxNbRooms IS NULL OR p.nb_rooms <= :maxNbRooms) AND
            (:isAvailable IS NULL OR p.is_sold != :isAvailable) --AND
            --(:commodities IS NULL OR c.id IN (:commodities))
            AND ((:dateOfSaleStart IS NULL OR :dateOfSaleEnd IS NULL) OR (p.date_of_sale BETWEEN :dateOfSaleStart AND :dateOfSaleEnd))
        GROUP BY p.id
    """)
    suspend fun search(
        type: Long?,
        minPrice: Double?,
        maxPrice: Double?,
        minSurface: Double?,
        maxSurface: Double?,
        minNbRooms: Int?,
        maxNbRooms: Int?,
        isAvailable: Boolean?,
        dateOfSaleStart : Long?,
        dateOfSaleEnd : Long?
        //commodities: List<Long>?
    ): List<PropertyListItemFlatten>

    @RawQuery
    suspend fun search(query: SupportSQLiteQuery): List<PropertyListItemFlatten>
}