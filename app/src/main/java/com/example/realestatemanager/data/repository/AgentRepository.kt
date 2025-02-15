package com.example.realestatemanager.data.repository

import androidx.annotation.WorkerThread
import com.example.realestatemanager.data.dao.AgentDAO
import com.example.realestatemanager.domain.model.Agent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class AgentRepository @Inject constructor(private val agentDAO: AgentDAO) {
    @WorkerThread
    suspend fun insert(agent: Agent): Long {
        return agentDAO.insert(agent.toDTO())
    }

    @WorkerThread
    suspend fun insert(agents: List<Agent>): List<Long> {
        return agentDAO.insert(agents.map { it.toDTO() })
    }

    @WorkerThread
    suspend fun insertAsResult(agent: Agent): Result<Long>{
        return try {
            Result.success(agentDAO.insert(agent.toDTO()))
        }catch (e: Exception) {
            Result.failure(e)
        }
    }

    @WorkerThread
    suspend fun insertAsResult(agents : List<Agent>): Result<List<Long>>{
        return try {
            Result.success(agentDAO.insert(agents.map { it.toDTO() }))
        }catch (e : Exception){
            Result.failure(e)
        }
    }

    fun getById(id : Long) : Flow<Agent?> {
        return agentDAO.getById(id).map { it?.toModel() }
    }

    fun getAll(): Flow<List<Agent>> {
        return agentDAO.getAllRT().map { list -> list.map { it.toModel() } }
    }

    @WorkerThread
    suspend fun getAllAsResult(): Result<List<Agent>> {
        return try {
            Result.success(agentDAO.getAll().map { it.toModel() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    @WorkerThread
    suspend fun delete(agent : Agent){
        agentDAO.delete(agent.toDTO())
    }

    @WorkerThread
    suspend fun deleteAll(){
        agentDAO.deleteAll()
    }

    @WorkerThread
    suspend fun deleteById(id: Long){
        agentDAO.deleteById(id)
    }

    @WorkerThread
    suspend fun update(agent: Agent): Int{
        return agentDAO.update(agent.toDTO())
    }
}