package com.example.realestatemanager.data.repository

import android.util.Log
import androidx.annotation.WorkerThread
import com.example.realestatemanager.data.dao.CommodityDAO
import com.example.realestatemanager.data.dao.CommodityPropertyCrossRefDAO
import com.example.realestatemanager.data.dao.LocationDAO
import com.example.realestatemanager.data.dao.PictureDAO
import com.example.realestatemanager.data.dao.PropertyDAO
import com.example.realestatemanager.data.entity.CommodityPropertyCrossRefDTO
import com.example.realestatemanager.domain.model.Property
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class EasyPropertyRepository @Inject constructor(
    private val locationDAO: LocationDAO,
    private val propertyDAO: PropertyDAO,
    private val pictureDAO: PictureDAO,
    private val commodityDAO: CommodityDAO,
    private val commodityPropertyCrossRefDAO: CommodityPropertyCrossRefDAO
) {

    /** INSERT **/
    @WorkerThread
    suspend fun insert(property: Property): Long {
        var propertyCopy = property.copy()
        if(property.location != null) {
            val location = property.location
            val locationID = locationDAO.search(location.street, location.streetNumber, location.postalCode, location.city, location.country).first()
            if(locationID == null) {
                val id = locationDAO.insert(location.toDTO())
                propertyCopy = propertyCopy.copy(location = location.copy(id = id))
            } else {
                propertyCopy = propertyCopy.copy(location = location.copy(id = locationID))
            }
        }
        val propertyId = propertyDAO.insert(propertyCopy.toDTO())
        propertyCopy.commodities.forEach { commodity ->
            val commodityId = commodityDAO.insert(commodity.toDTO())
            commodityPropertyCrossRefDAO.insert(CommodityPropertyCrossRefDTO(propertyId, commodityId))
        }
        propertyCopy.pictures.forEach { picture ->
            pictureDAO.insert(picture.toDTO(propertyId))
        }
        locationDAO.deleteUnused()
        pictureDAO.deleteUnused()
        return propertyId
    }

    /** UPDATE **/
    @WorkerThread
    suspend fun update(property: Property) {
        if (property.id == null) {
            throw IllegalArgumentException("Property ID must not be null")
        }
        val propertyId: Long = property.id?:0
        var propertyCopy = property.copy()
        if (property.location != null) {
            val location = property.location
            val locationID = locationDAO.search(
                location.street,
                location.streetNumber,
                location.postalCode,
                location.city,
                location.country
            ).first()
            if(locationID == null) {
                val id = locationDAO.insert(location.toDTO())
                propertyCopy = propertyCopy.copy(location = location.copy(id = id))
            } else {
                propertyCopy = propertyCopy.copy(location = location.copy(id = locationID))
            }
        }
        Log.d("EASYPROPERTYREPO", "location city : ${propertyCopy.location?.city}")
        propertyDAO.update(propertyCopy.toDTO())
        commodityPropertyCrossRefDAO.deleteByPropertyId(propertyId)
        propertyCopy.commodities.forEach { commodity ->
            val commodityId = commodityDAO.insert(commodity.toDTO())
            commodityPropertyCrossRefDAO.insert(
                CommodityPropertyCrossRefDTO(
                    propertyId,
                    commodityId
                )
            )
        }
        pictureDAO.deleteByPropertyId(propertyId)
        propertyCopy.pictures.forEach { picture ->
            pictureDAO.insert(picture.toDTO(propertyId))
        }
        locationDAO.deleteUnused()
        pictureDAO.deleteUnused()
    }

    /** DELETE **/
    @WorkerThread
    suspend fun delete(property: Property) {
        if (property.id == null) {
            throw IllegalArgumentException("Property ID must not be null")
        }
        val propertyId: Long = property.id?:0
        commodityPropertyCrossRefDAO.deleteByPropertyId(propertyId)
        pictureDAO.deleteByPropertyId(propertyId)
        propertyDAO.deleteById(propertyId)
        locationDAO.deleteUnused()
        pictureDAO.deleteUnused()
    }
}