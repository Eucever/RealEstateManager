package com.example.realestatemanager.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.realestatemanager.domain.model.*
import org.threeten.bp.Instant


@Entity(tableName = "property",
    foreignKeys = [
        ForeignKey(
            entity = LocationDTO::class,
            parentColumns = ["id"],
            childColumns = ["location_id"],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = AgentDTO::class,
            parentColumns = ["id"],
            childColumns = ["agent_id"],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = EstateTypeDTO::class,
            parentColumns = ["id"],
            childColumns = ["type_id"],
            onDelete = ForeignKey.SET_NULL
        )
    ])
data class PropertyDTO(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id : Long?,

    @ColumnInfo(name = "name")
    val name : String,

    @ColumnInfo(name = "description")
    val description : String?,

    @ColumnInfo(name = "surface")
    val surface : Double?,

    @ColumnInfo(name = "nb_rooms")
    val nbRooms : Int?,

    @ColumnInfo(name = "apartment_number")
    val apartmentNumber : Int?,

    @ColumnInfo(name = "nb_bathrooms")
    val nbBathrooms : Int?,

    @ColumnInfo(name = "nb_bedrooms")
    val nbBedrooms : Int?,

    @ColumnInfo(name = "price")
    val price : Double?,

    @ColumnInfo(name = "is_Sold")
    val isSold : Boolean,

    @ColumnInfo(name = "date_of_creation")
    val dateCreation : Instant,

    @ColumnInfo(name = "date_of_entry")
    val dateEntry : Instant?,

    @ColumnInfo(name = "date_of_sale")
    val dateSale : Instant?,

    @ColumnInfo(name = "agent_id", index = true)
    val agentId : Long?,

    @ColumnInfo(name = "type_id")
    val typeId : Long?,

    @ColumnInfo(name = "location_id")
    val locationId : Long?,
)
{
    fun toModel(): Property {
        val typeDM = EstateType (id = typeId, name = "" ,description = "")
        val locationDM = Location (id = locationId, street = "", streetNumber = null, postalCode = "", city = "", country = "", latitude = null, longitude = null)
        val agentDM = Agent(id = agentId, lastName = "", firstName = "", email = "", phone = "" )
        val picturesDM = emptyList<Picture>()
        val commoditiesDM = emptyList<Commodity>()
        return Property(
            id = if (this.id == 0L) null else this.id,
            name = this.name,
            type = typeDM,
            description = this.description,
            surface = this.surface,
            nbRooms = this.nbRooms,
            nbBathrooms = this.nbBathrooms,
            nbBedrooms = this.nbBedrooms,
            location = locationDM,
            price = this.price,
            pictures = picturesDM,
            isSold = this.isSold,
            dateCreation= this.dateCreation,
            apartmentNumber = this.apartmentNumber,
            dateEntry = this.dateEntry,
            dateSale = this.dateSale,
            agent = agentDM,
            commodities = commoditiesDM,
        )
    }
}
