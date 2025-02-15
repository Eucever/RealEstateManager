package com.example.realestatemanager.domain.usecase.commodity

import com.example.realestatemanager.data.repository.CommodityRepository
import com.example.realestatemanager.domain.model.Commodity
import javax.inject.Inject

class GetAllCommoditiesUseCase @Inject constructor(
    private val commodityRepository: CommodityRepository
) {
    suspend operator fun invoke() : Result<List<Commodity>> = commodityRepository.getAllAsResult()
}