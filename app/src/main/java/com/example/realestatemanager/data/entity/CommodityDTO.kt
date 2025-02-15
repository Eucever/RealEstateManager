package com.example.realestatemanager.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.realestatemanager.domain.model.Commodity


@Entity(tableName = "commodity")
data class CommodityDTO(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id : Long?,

    @ColumnInfo(name = "type")
    val type : String
)
{
    @Ignore
    fun toModel(): Commodity {
        return Commodity(
            id = if (this.id == 0L) null else this.id,
            type = this.type
        )
    }
}
