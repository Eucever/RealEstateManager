package com.example.realestatemanager.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.realestatemanager.domain.model.EstateType

@Entity(tableName = "estate_type")
data class EstateTypeDTO(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long?,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "description")
    val description: String
)
{
    @Ignore
    fun toModel(): EstateType {
        return EstateType(
            id = if (this.id == 0L) null else this.id,
            name = this.name,
            description = this.description
        )
    }
}
