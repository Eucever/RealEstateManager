package com.example.realestatemanager.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "commodity_property",
    foreignKeys = [
        ForeignKey(
            entity = CommodityDTO::class,
            parentColumns = ["id"],
            childColumns = ["commodity_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PropertyDTO::class,
            parentColumns = ["id"],
            childColumns = ["property_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    primaryKeys = ["commodity_id", "property_id"],
    indices = [
        androidx.room.Index(value = ["commodity_id"]),
        androidx.room.Index(value = ["property_id"])
    ]
)
data class CommodityPropertyCrossRefDTO(
    @ColumnInfo(name = "property_id")
    val propertyId: Long,

    @ColumnInfo(name = "commodity_id")
    val commodityId: Long
)
