package com.example.realestatemanager

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.realestatemanager.data.dao.AgentDAO
import com.example.realestatemanager.data.database.RealEstateDatabase
import com.example.realestatemanager.data.repository.AgentRepository
import com.example.realestatemanager.domain.model.Agent
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject


@RunWith(AndroidJUnit4::class)
class AgentRepositoryTest {

    @Inject
    lateinit var agentDAO: AgentDAO
    private lateinit var agentRepository: AgentRepository
    private lateinit var roomDatabase: RealEstateDatabase

    @Before
    fun setup(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        roomDatabase = Room.inMemoryDatabaseBuilder(context, RealEstateDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        agentDAO = roomDatabase.agentDao()
        agentRepository = AgentRepository(agentDAO)
    }

    @After
    fun teardown(){
        roomDatabase.close()
    }

    @Test
    fun insertGetAgent() = runTest{
        val agent = Agent(id=null, lastName = "Smith", firstName = "John", email = "johnsmith@gmail.com", phone = "065432111")
        val id = agentRepository.insert(agent)

        assertTrue(id > 0)
        val agentInDb = agentRepository.getById(id).first()

        assertNotNull(agentInDb)
        assertEquals(agent.firstName, agentInDb?.firstName)
        assertEquals(agent.lastName, agentInDb?.lastName)
    }

    @Test
    fun updateAgent() = runTest {
        val agent = Agent(id = null, lastName = "Smith", firstName = "John", email = "johnsmith@gmail.com", phone = "065432111")
        val id = agentRepository.insert(agent)
        val agentUpdated = agent.copy(id = id, firstName = "Bob")

        val rowsUpdated = agentRepository.update(agentUpdated)
        assertEquals(1, rowsUpdated)

        val agentFromDb = agentRepository.getById(id).first()
        assertNotNull(agentFromDb)
        assertEquals(agentUpdated.firstName, agentFromDb?.firstName)
    }

    @Test
    fun deleteAgent() = runTest {
        val agent = Agent(id = null, lastName = "Smith", firstName = "John", email = "johnsmith@gmail.com", phone = "065432111")
        val id = agentRepository.insert(agent)

        agentRepository.deleteById(id)

        val agentFromDb = agentRepository.getById(id).first()
        assertNull(agentFromDb)
    }

    @Test
    fun insertGetAgentsList() = runTest {
        val agents = listOf(
            Agent(id = null, firstName = "Smith", lastName = "John", email = "johnsmith@gmail.com", phone = "065432111"),
            Agent(id = null, firstName = "Doe", lastName = "Bob", email = "bobdoe@gmail.com", phone = "0645768554")
        )

        val ids = agentRepository.insert(agents)
        assertTrue(ids.size == agents.size)

        val allAgents = agentRepository.getAll().first()
        assertEquals(agents.size, allAgents.size)
    }

    @Test
    fun deleteAllAgents() = runTest {
        val agents = listOf(
            Agent(id = null, firstName = "Smith", lastName = "John", email = "johnsmith@gmail.com", phone = "065432111"),
            Agent(id = null, firstName = "Doe", lastName = "Bob", email = "bobdoe@gmail.com", phone = "0645768554")
        )

        val ids = agentRepository.insert(agents)
        assertTrue(ids.size == agents.size)
        agentRepository.deleteAll()

        val allAgents = agentRepository.getAll().first()
        assertTrue(allAgents.isEmpty())
    }

    @Test
    fun getAllAgents()= runTest {
        val agents = listOf(
            Agent(id = null, firstName = "Smith", lastName = "John", email = "johnsmith@gmail.com", phone = "065432111"),
            Agent(id = null, firstName = "Doe", lastName = "Bob", email = "bobdoe@gmail.com", phone = "0645768554")
        )

        agentRepository.insert(agents)

        val agentsFlow = agentRepository.getAll().first()
        assertEquals(agents.size, agentsFlow.size)

    }



}