package com.example.realestatemanager.domain.model

import com.example.realestatemanager.data.entity.EstateTypeDTO

data class EstateType(
    val id: Long?,
    val name: String,
    val description: String
){
    fun toDTO(): EstateTypeDTO {
        return EstateTypeDTO(
            id = this.id,
            name = this.name,
            description = this.description
        )
    }
}
