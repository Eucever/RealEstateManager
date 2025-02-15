package com.example.realestatemanager.domain.model

import android.graphics.Bitmap
import com.example.realestatemanager.data.entity.PictureDTO

data class Picture(
    val id : Long?,
    val content : Bitmap,
    val thumbnailContent : Bitmap,
    val description : String?,
    val order : Int
)
{
    fun toDTO(propertyId : Long): PictureDTO{
        return PictureDTO(
            id = this.id,
            content = this.content,
            thumbnailContent = this.content,
            description = this.description,
            order = this.order,
            propertyId = propertyId
        )
    }
}
