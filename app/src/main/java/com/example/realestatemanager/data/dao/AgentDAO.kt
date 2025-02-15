package com.example.realestatemanager.data.dao

import androidx.room.*
import com.example.realestatemanager.data.entity.AgentDTO
import kotlinx.coroutines.flow.Flow


@Dao
interface AgentDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(agent: AgentDTO): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(agents : List<AgentDTO>): List<Long>

    @Delete
    suspend fun delete(agent: AgentDTO)

    @Query("DELETE FROM agent")
    suspend fun deleteAll()

    @Query("DELETE FROM agent WHERE id=:id")
    suspend fun deleteById(id: Long)

    @Update
    suspend fun update(agent: AgentDTO): Int

    /**SELECT REALTIME/FLOW **/
    @Query("SELECT * FROM agent")
    fun getAllRT(): Flow<List<AgentDTO>>

    @Query("SELECT * FROM agent WHERE id=:id")
    fun getById(id:Long): Flow<AgentDTO?>

    /** SELECT SUSPENDED**/
    @Query("SELECT * FROM agent")
    suspend fun getAll(): List<AgentDTO>
}