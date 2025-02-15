package com.example.realestatemanager.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.realestatemanager.domain.model.Agent

@Entity(tableName = "agent")
data class AgentDTO(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id : Long?,

    @ColumnInfo(name = "last_name")
    val lastName : String,

    @ColumnInfo(name = "first_name")
    val firstName : String,

    @ColumnInfo(name = "email")
    val email : String,

    @ColumnInfo(name = "phone")
    val phone : String
)
{
    @Ignore
    fun toModel(): Agent {
        return Agent(
            id = if (this.id == 0L) null else this.id,
            lastName = this.lastName,
            firstName = this.firstName,
            email = this.email,
            phone = this.phone
        )
    }
}