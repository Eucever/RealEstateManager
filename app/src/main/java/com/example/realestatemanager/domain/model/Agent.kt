package com.example.realestatemanager.domain.model

import com.example.realestatemanager.data.entity.AgentDTO
import com.example.realestatemanager.data.entity.EstateTypeDTO

data class Agent(
    val id : Long?,
    val lastName : String,
    val firstName : String,
    val email : String,
    val phone : String
)
{
    fun toDTO(): AgentDTO {
        return AgentDTO(
            id = this.id,
            lastName =this.lastName,
            firstName = this.firstName,
            email = this.email,
            phone = this.phone
        )
    }
}
