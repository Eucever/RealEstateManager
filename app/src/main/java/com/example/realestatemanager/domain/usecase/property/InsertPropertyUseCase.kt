package com.example.realestatemanager.domain.usecase.property

import com.example.realestatemanager.data.repository.EasyPropertyRepository
import com.example.realestatemanager.domain.model.Property
import javax.inject.Inject

class InsertPropertyUseCase @Inject constructor(
    val easyPropertyRepository: EasyPropertyRepository
) {
    suspend operator fun invoke(property: Property): Long{
        return easyPropertyRepository.insert(property)
    }
}