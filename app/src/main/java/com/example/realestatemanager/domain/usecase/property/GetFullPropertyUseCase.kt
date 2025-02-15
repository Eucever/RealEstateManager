package com.example.realestatemanager.domain.usecase.property

import com.example.realestatemanager.data.repository.FullPropertyRepository
import javax.inject.Inject

class GetFullPropertyUseCase @Inject constructor(
    private val fullPropertyRepository: FullPropertyRepository
) {
    suspend operator fun invoke(id: Long) = fullPropertyRepository.getById(id)
}