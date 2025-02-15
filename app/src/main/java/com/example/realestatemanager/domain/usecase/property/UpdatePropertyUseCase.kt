package com.example.realestatemanager.domain.usecase.property

import android.util.Log
import com.example.realestatemanager.data.repository.EasyPropertyRepository
import com.example.realestatemanager.domain.model.Property
import javax.inject.Inject

class UpdatePropertyUseCase @Inject constructor(
    private val easyPropertyRepository: EasyPropertyRepository
) {
    suspend operator fun invoke(property: Property) {
        easyPropertyRepository.update(property)
        Log.d("USECASE PROPERTY", "CITY : ${property.location?.city}")
    }
}