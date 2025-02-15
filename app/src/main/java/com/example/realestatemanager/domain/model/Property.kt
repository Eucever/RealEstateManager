package com.example.realestatemanager.domain.model

import com.example.realestatemanager.data.entity.PropertyDTO
import org.threeten.bp.Instant

data class Property(
    val id : Long?,
    val name: String,
    val type : EstateType?,
    val description : String?,
    val surface : Double?,
    val nbRooms : Int?,
    val nbBathrooms : Int?,
    val nbBedrooms : Int?,
    val apartmentNumber : Int?,
    val location : Location?,
    val price : Double?,
    val pictures : List<Picture>,
    val isSold : Boolean,
    val dateCreation : Instant,
    val dateEntry : Instant?,
    val dateSale : Instant?,
    val agent : Agent?,
    val commodities: List<Commodity>,
)
{
    fun toDTO(): PropertyDTO{
        return PropertyDTO(
            id = this.id,
            name = this.name,
            description = this.description,
            surface = this.surface,
            nbRooms = this.nbRooms,
            nbBathrooms = this.nbBathrooms,
            nbBedrooms = this.nbBedrooms,
            apartmentNumber = this.apartmentNumber,
            price = this.price,
            isSold = this.isSold,
            dateCreation = this.dateCreation,
            dateEntry = this.dateEntry,
            dateSale = this.dateSale,
            agentId = this.agent?.id,
            locationId = this.location?.id,
            typeId = this.type?.id,
        )
    }
}
