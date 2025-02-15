package com.example.realestatemanager.domain.usecase.agent

import com.example.realestatemanager.data.repository.AgentRepository
import com.example.realestatemanager.domain.model.Agent
import javax.inject.Inject

class GetAllAgentsUseCase @Inject constructor(
    private val agentRepository: AgentRepository
) {
    suspend operator fun invoke(): Result<List<Agent>> = agentRepository.getAllAsResult()
}