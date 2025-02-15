package com.example.realestatemanager.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.realestatemanager.domain.model.Location


@Entity(tableName = "location")
data class LocationDTO(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id : Long?,

    @ColumnInfo(name = "street")
    val street : String,

    @ColumnInfo(name = "street_number")
    val streetNumber : Int?,

    @ColumnInfo(name = "postal_Code")
    val postalCode : String,

    @ColumnInfo(name = "city")
    val city : String,

    @ColumnInfo(name = "country")
    val country : String,

    @ColumnInfo(name = "longitude")
    val longitude : Double?,

    @ColumnInfo(name = "latitude")
    val latitude : Double?
)
{
    @Ignore
    fun toModel(): Location {
        return Location(
                id = if (this.id == 0L) null else this.id,
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
