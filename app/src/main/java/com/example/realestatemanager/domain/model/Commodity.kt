package com.example.realestatemanager.domain.model

import com.example.realestatemanager.data.entity.CommodityDTO

data class Commodity(
    val id : Long?,
    val type : String
)
{
    fun toDTO(): CommodityDTO{
        return CommodityDTO(
            id = this.id,
            type = this.type,
        )
    }
}
