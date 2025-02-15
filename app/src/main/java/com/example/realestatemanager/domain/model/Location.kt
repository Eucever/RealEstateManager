package com.example.realestatemanager.domain.model

import com.example.realestatemanager.data.entity.LocationDTO

data class Location(
    val id : Long?,
    val street : String,
    val streetNumber : Int?,
    val postalCode : String,
    val city : String,
    val country : String,
    val longitude : Double?,
    val latitude : Double?
)
{
    fun toDTO(): LocationDTO{
        return LocationDTO(
            id = this.id,
            street = this.street,
            streetNumber = this.streetNumber,
            postalCode = this.postalCode,
            city = this.city,
            country = this.country,
            longitude = this.longitude,
            latitude = this.latitude
        )
    }
}
