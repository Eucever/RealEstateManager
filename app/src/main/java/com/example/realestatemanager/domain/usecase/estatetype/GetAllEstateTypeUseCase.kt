package com.example.realestatemanager.domain.usecase.estatetype

import com.example.realestatemanager.data.repository.EstateTypeRepository
import com.example.realestatemanager.domain.model.EstateType
import javax.inject.Inject

class GetAllEstateTypesUseCase@Inject constructor(
    private val estateTypeRepository: EstateTypeRepository
) {
    suspend operator fun invoke() : Result<List<EstateType>> = estateTypeRepository.getAllAsResult()
}