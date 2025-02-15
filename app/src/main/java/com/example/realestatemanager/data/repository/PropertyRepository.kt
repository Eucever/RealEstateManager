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

        //val dateSaleStart = Instant.now().toEpochMilli()-(3600L*24L*30L*3L*1000L)
        //val dateSaleEnd  = Instant.now().toEpochMilli()
        //Log.d("PROPREPO", "Date start : $dateSaleStart")
        //Log.d("PROPREPO", "Date end : $dateSaleEnd")
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

    /*@WorkerThread
    suspend fun searchRaw(
        type: Long?,
        minPrice: Double?,
        maxPrice: Double?,
        minSurface: Double?,
        maxSurface: Double?,
        minNbRooms: Int?,
        maxNbRooms: Int?,
        isAvailable: Boolean?,
        commodities: List<Long>?
    ): List<PropertyListItemFlatten> {
        var query = """
        SELECT
            p.id,
            p.name,
            p.description,
            p.surface,
            p.nb_rooms AS nbRooms,
            p.price,
            p.is_sold AS isSold,
            p.creation_date AS creationDate,
            p.entry_date AS entryDate,
            p.sale_date AS saleDate,
            et.name AS type,
            l.street,
            l.postal_code AS postalCode,
            l.city,
            l.country,
            l.longitude,
            l.latitude,
            a.first_name || ' ' || a.last_name AS agentName,
            COALESCE(GROUP_CONCAT(c.name,','),'') AS commoditiesName,
            pi.content AS picture
        FROM property p
        LEFT OUTER JOIN estate_type et ON et.id = p.type_id
        LEFT OUTER JOIN location l ON l.id = p.location_id
        LEFT OUTER JOIN agent a ON a.id = p.agent_id
        LEFT OUTER JOIN property_commodity pc ON pc.property_id = p.id
        LEFT OUTER JOIN commodity c ON c.id = pc.commodity_id
        LEFT OUTER JOIN picture pi ON pi.property_id = p.id AND pi.`order` = 0
    """.trimIndent()

        val selectionArgs = mutableListOf<Any?>()
        val selectionConditions = mutableListOf<String>()

        // Ajout des conditions selon les paramètres
        type?.let {
            selectionConditions.add("et.id = ?")
            selectionArgs.add(it)
        }
        minPrice?.let {
            selectionConditions.add("p.price >= ?")
            selectionArgs.add(it)
        }
        maxPrice?.let {
            selectionConditions.add("p.price <= ?")
            selectionArgs.add(it)
        }
        minSurface?.let {
            selectionConditions.add("p.surface >= ?")
            selectionArgs.add(it)
        }
        maxSurface?.let {
            selectionConditions.add("p.surface <= ?")
            selectionArgs.add(it)
        }
        minNbRooms?.let {
            selectionConditions.add("p.nb_rooms >= ?")
            selectionArgs.add(it)
        }
        maxNbRooms?.let {
            selectionConditions.add("p.nb_rooms <= ?")
            selectionArgs.add(it)
        }
        isAvailable?.let {
            selectionConditions.add("p.is_sold = ?")
            selectionArgs.add(if (it) 0 else 1)    }
        commodities?.let {
            if (it.isNotEmpty()) {
                selectionConditions.add("c.id IN (${it.joinToString(",")})")
            }
        }

        if (selectionConditions.isNotEmpty()) {
            query += " WHERE " + selectionConditions.joinToString(" AND ")
        }

        query += " GROUP BY p.id"
        val sqlQuery = SupportSQLiteQuery.builder(query).bindArgs(*selectionArgs.toTypedArray()).create()


        return propertyDAO.search(sqlQuery).map { it.toModel() }
    }*/

}