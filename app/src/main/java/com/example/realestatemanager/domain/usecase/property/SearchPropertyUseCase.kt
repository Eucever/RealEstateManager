package com.example.realestatemanager.domain.usecase.property

import com.example.realestatemanager.data.repository.PropertyRepository
import com.example.realestatemanager.domain.model.Property
import org.threeten.bp.Instant
import javax.inject.Inject

class SearchPropertiesUseCase @Inject constructor(
    private val propertyRepository: PropertyRepository
) {
    suspend operator fun invoke(
        type: Long?,
        minPrice: Double?,
        maxPrice: Double?,
        minSurface: Double?,
        maxSurface: Double?,
        minNbRooms: Int?,
        maxNbRooms: Int?,
        isAvailable: Boolean?,
        commodities: List<Long>?,
        dateSale : Instant?
    ): List<Property> {
        return propertyRepository.search(
            type,
            minPrice,
            maxPrice,
            minSurface,
            maxSurface,
            minNbRooms,
            maxNbRooms,
            isAvailable,
            commodities,
            dateSale,
            Instant.now()
        )
    }
}