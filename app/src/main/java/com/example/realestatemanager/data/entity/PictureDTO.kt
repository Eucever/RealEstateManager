package com.example.realestatemanager.data.entity

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.realestatemanager.domain.model.Picture


@Entity(
    tableName = "picture",
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = PropertyDTO::class,
            parentColumns = ["id"],
            childColumns = ["property_id"],
            onDelete = androidx.room.ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["property_id"])]
)
data class PictureDTO(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id : Long?,

    @ColumnInfo(name = "content")
    val content : Bitmap,

    @ColumnInfo(name = "thumbnail_content")
    val thumbnailContent : Bitmap,

    @ColumnInfo(name = "description")
    val description : String?,

    @ColumnInfo(name = "order")
    val order : Int,

    @ColumnInfo(name = "property_id")
    val propertyId: Long
)
{
    @Ignore
    fun toModel(): Picture {
        return Picture(
            id = if (this.id == 0L) null else this.id,
            content = this.content,
            thumbnailContent = this.thumbnailContent,
            description = this.description,
            order = this.order
        )
    }
}
