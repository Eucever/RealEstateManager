package com.example.realestatemanager.data.projection

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.realestatemanager.data.entity.AgentDTO
import com.example.realestatemanager.data.entity.CommodityDTO
import com.example.realestatemanager.data.entity.CommodityPropertyCrossRefDTO
import com.example.realestatemanager.data.entity.EstateTypeDTO
import com.example.realestatemanager.data.entity.LocationDTO
import com.example.realestatemanager.data.entity.PictureDTO
import com.example.realestatemanager.data.entity.PropertyDTO
import com.example.realestatemanager.domain.model.Property

class FullPropertyProjection(
    @Embedded
    val property: PropertyDTO,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            CommodityPropertyCrossRefDTO::class,
            parentColumn = "property_id",
            entityColumn = "commodity_id"
        )
    )
    val commodities: List<CommodityDTO>,

    @Relation(
        parentColumn = "location_id",
        entityColumn = "id"
    )
    val location: LocationDTO?,

    @Relation(
        parentColumn = "type_id",
        entityColumn = "id"
    )
    val estateType: EstateTypeDTO?,

    @Relation(
        parentColumn = "agent_id",
        entityColumn = "id"
    )
    val agent: AgentDTO?,

    @Relation(
        parentColumn = "id",
        entityColumn = "property_id"
    )
    val pictures: List<PictureDTO>
){
    fun toModel(): Property {
        return Property(
            id = property.id,
            name = property.name,
            description = property.description,
            surface = property.surface,
            nbRooms = property.nbRooms,
            nbBathrooms = property.nbBathrooms,
            nbBedrooms = property.nbBedrooms,
            price = property.price,
            isSold = property.isSold,
            dateCreation = property.dateCreation,
            dateEntry = property.dateEntry,
            dateSale = property.dateSale,
            apartmentNumber = property.apartmentNumber,
            type = estateType?.toModel(),
            location = location?.toModel(),
            agent = agent?.toModel(),
            commodities = commodities.map { it.toModel() },
            pictures = pictures.map { it.toModel() }
        )
    }
}

