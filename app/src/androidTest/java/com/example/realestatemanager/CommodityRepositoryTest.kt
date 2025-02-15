package com.example.realestatemanager

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.realestatemanager.data.dao.CommodityDAO
import com.example.realestatemanager.data.database.RealEstateDatabase
import com.example.realestatemanager.data.repository.AgentRepository
import com.example.realestatemanager.data.repository.CommodityRepository
import com.example.realestatemanager.domain.model.Agent
import com.example.realestatemanager.domain.model.Commodity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject


@RunWith(AndroidJUnit4::class)
class CommodityRepositoryTest {

    @Inject
    lateinit var commodityDAO: CommodityDAO
    private lateinit var commodityRepository: CommodityRepository
    private lateinit var roomDatabase: RealEstateDatabase

    @Before
    fun setup(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        roomDatabase = Room.inMemoryDatabaseBuilder(context, RealEstateDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        commodityDAO = roomDatabase.commodityDao()
        commodityRepository = CommodityRepository(commodityDAO)
    }

    @After
    fun teardown(){
        roomDatabase.close()
    }

    @Test
    fun insertGetCommodity() = runTest{
        val commodity = Commodity(id = null , type = "Re")
        val id = commodityRepository.insert(commodity)

        assertTrue(id > 0)
        val commodityInDb = commodityRepository.getById(id).first()

        assertNotNull(commodityInDb)
        assertEquals(commodity.type, commodityInDb?.type)
    }

    @Test
    fun updateCommodity() = runTest {
        val commodity = Commodity(id = null, type = "Restaurant")
        val id = commodityRepository.insert(commodity)
        val commodityUpdate = Commodity(id = id, type = "Pharmacy")

        val rowsUpdated = commodityRepository.update(commodityUpdate)
        assertEquals(1, rowsUpdated)

        val commodityFromDb = commodityRepository.getById(id).first()
        assertNotNull(commodityFromDb)
        assertEquals(commodityUpdate.type, commodityFromDb?.type)
    }

    @Test
    fun deleteCommodity() = runTest {
        val commodity = Commodity(id = null, type = "Restaurant")
        val id = commodityRepository.insert(commodity)

        commodityRepository.deleteById(id)

        val commodityFromDb = commodityRepository.getById(id).first()
        assertNull(commodityFromDb)
    }

    @Test
    fun insertGetCommoditiesList() = runTest {
        val commodities = listOf(
            Commodity(id = null, type = "Restaurant"),
            Commodity(id = null, type = "Pharmacy")
        )

        val ids = commodityRepository.insert(commodities)
        assertTrue(ids.size == commodities.size)

        val allAgents = commodityRepository.getAll().first()
        assertEquals(commodities.size, allAgents.size)
    }

    @Test
    fun deleteAllAgents() = runTest {
        val commodities = listOf(
            Commodity(id = null, type = "Restaurant"),
            Commodity(id = null, type = "Pharmacy")
        )

        val ids = commodityRepository.insert(commodities)
        assertTrue(ids.size == commodities.size)
        commodityRepository.deleteAll()

        val allAgents = commodityRepository.getAll().first()
        assertTrue(allAgents.isEmpty())
    }

    @Test
    fun getAllAgents()= runTest {
        val commodities = listOf(
            Commodity(id = null, type = "Restaurant"),
            Commodity(id = null, type = "Pharmacy")
        )

        commodityRepository.insert(commodities)

        val commoditiesFlow = commodityRepository.getAll().first()
        assertEquals(commodities.size, commoditiesFlow.size)

    }





}