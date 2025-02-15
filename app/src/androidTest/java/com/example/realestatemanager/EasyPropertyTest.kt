package com.example.realestatemanager

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.realestatemanager.data.dao.AgentDAO
import com.example.realestatemanager.data.dao.CommodityDAO
import com.example.realestatemanager.data.dao.CommodityPropertyCrossRefDAO
import com.example.realestatemanager.data.dao.EstateTypeDAO
import com.example.realestatemanager.data.dao.LocationDAO
import com.example.realestatemanager.data.dao.PictureDAO
import com.example.realestatemanager.data.dao.PropertyDAO
import com.example.realestatemanager.data.database.RealEstateDatabase
import com.example.realestatemanager.data.repository.AgentRepository
import com.example.realestatemanager.data.repository.EasyPropertyRepository
import com.example.realestatemanager.data.repository.EstateTypeRepository
import com.example.realestatemanager.domain.model.Agent
import com.example.realestatemanager.domain.model.Commodity
import com.example.realestatemanager.domain.model.EstateType
import com.example.realestatemanager.domain.model.Location
import com.example.realestatemanager.domain.model.Picture
import com.example.realestatemanager.domain.model.Property
import com.example.realestatemanager.utils.BitmapUtils
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.threeten.bp.Instant


@RunWith(AndroidJUnit4::class)
class EasyPropertyTest {
    private lateinit var agentDAO: AgentDAO
    private lateinit var estateTypeDAO: EstateTypeDAO
    private lateinit var propertyDAO: PropertyDAO
    private lateinit var locationDAO: LocationDAO
    private lateinit var pictureDAO: PictureDAO
    private lateinit var commodityDAO: CommodityDAO
    private lateinit var commodityPropertyCrossRefDAO: CommodityPropertyCrossRefDAO
    private lateinit var propertyRepository: EasyPropertyRepository
    private lateinit var agentRepository: AgentRepository
    private lateinit var estateTypeRepository: EstateTypeRepository
    private lateinit var roomDatabase: RealEstateDatabase

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        roomDatabase = Room.inMemoryDatabaseBuilder(context, RealEstateDatabase::class.java)
            .allowMainThreadQueries()
            .setJournalMode(RoomDatabase.JournalMode.AUTOMATIC)
            .build()
        agentDAO = roomDatabase.agentDao()
        estateTypeDAO = roomDatabase.estateTypeDao()
        propertyDAO = roomDatabase.propertyDao()
        locationDAO = roomDatabase.locationDao()
        pictureDAO = roomDatabase.pictureDao()
        commodityDAO = roomDatabase.commodityDao()
        commodityPropertyCrossRefDAO = roomDatabase.commodityPropertyCrossRefDao()
        propertyRepository = EasyPropertyRepository(
            locationDAO, propertyDAO, pictureDAO, commodityDAO, commodityPropertyCrossRefDAO
        )
        agentRepository = AgentRepository(agentDAO)
        estateTypeRepository = EstateTypeRepository(estateTypeDAO)
    }

    @After
    fun tearDown() {
        roomDatabase.close()
    }

    @Test
    fun insertAndGetProperty() = runTest {
        val type = EstateType(id = 1, name = "Apartment", description = "Modern apartment")
        val agent = Agent(id = null, firstName = "Smith", lastName = "John", email = "johnsmith@gmail.com", phone = "065432111")
        agentRepository.insert(agent)
        estateTypeRepository.insert(type)
        val property = Property(
            id = null,
            name = "The property",
            description = "Beautiful property",
            surface = 75.0,
            nbRooms = 3,
            nbBathrooms = 2,
            nbBedrooms = 2,
            price = 250000.0,
            isSold = false,
            dateCreation = Instant.now(),
            dateEntry = Instant.now(),
            apartmentNumber = 101,
            dateSale = null,
            type = type,
            location = Location(
                id = null, street = "Main Street", streetNumber = 123, postalCode = "75001",
                city = "Paris", country = "France", longitude = 2.3522, latitude = 48.8566
            ),
            agent = agent,
            commodities = listOf(
                Commodity(id = null, type = "Pool"),
                Commodity(id = null, type = "Garden")
            ),
            pictures = listOf(
                Picture(id = null, description = "Living room", content = BitmapUtils.create(100,100), thumbnailContent = BitmapUtils.create(100,100), order = 1)
            )
        )

        val propertyId = propertyRepository.insert(property)
        assertTrue(propertyId > 0)

        val propertyFromDb = propertyDAO.getById(propertyId).first()
        assertNotNull(propertyFromDb)
        assertEquals(property.description, propertyFromDb?.description)
        assertEquals(property.surface, propertyFromDb?.surface)
    }

    @Test
    fun updateProperty() = runTest {
        val type = EstateType(id = 1, name = "Apartment", description = "Modern apartment")
        val agent = Agent(id = null, lastName = "Smith", firstName = "John", email = "johnsmith@gmail.com", phone = "065432111")
        agentRepository.insert(agent)
        estateTypeRepository.insert(type)
        val property = Property(
            id = null,
            name = "The property",
            description = "Beautiful property",
            surface = 75.0,
            nbRooms = 3,
            nbBathrooms = 2,
            nbBedrooms = 2,
            price = 250000.0,
            isSold = false,
            dateCreation = Instant.now(),
            apartmentNumber = 101,
            dateEntry = Instant.now(),
            dateSale = null,
            type = type,
            location = Location(
                id = null, street = "Main Street", streetNumber = 123, postalCode = "75001",
                city = "Paris", country = "France", longitude = 2.3522, latitude = 48.8566
            ),
            agent = agent,
            commodities = listOf(
                Commodity(id = null, type = "Pool"),
                Commodity(id = null, type = "Garden")
            ),
            pictures = listOf(
                Picture(id = null, description = "Living room", content = BitmapUtils.create(100,100), thumbnailContent = BitmapUtils.create(100,100), order = 1)
            )
        )

        val propertyId = propertyRepository.insert(property)
        assertTrue(propertyId > 0)

        val updatedProperty = property.copy(id = propertyId, description = "Updated property description")
        propertyRepository.update(updatedProperty)

        val propertyFromDb = propertyDAO.getById(propertyId).first()
        assertNotNull(propertyFromDb)
        assertEquals(updatedProperty.description, propertyFromDb?.description)
    }

    /*
    @Test
    fun searchProperty() = runTest {
        val type = EstateType(id = 1, name = "Apartment", description = "Modern apartment")
        val agent = Agent(id = null, lastName = "Smith", firstName = "John", email = "johnsmith@gmail.com", phone = "065432111")
        agentRepository.insert(agent)
        estateTypeRepository.insert(type)
        val property = Property(
            id = null,
            name = "The property",
            description = "Beautiful property",
            surface = 75.0,
            nbRooms = 3,
            nbBathrooms = 2,
            nbBedrooms = 2,
            price = 250000.0,
            isSold = false,
            dateCreation = Instant.now(),
            apartmentNumber = 101,
            dateEntry = Instant.now(),
            dateSale = null,
            type = type,
            location = Location(
                id = null, street = "Main Street", streetNumber = 123, postalCode = "75001",
                city = "Paris", country = "France", longitude = 2.3522, latitude = 48.8566
            ),
            agent = agent,
            commodities = listOf(
                Commodity(id = null, type = "Pool"),
                Commodity(id = null, type = "Garden")
            ),
            pictures = listOf(
                Picture(id = null, description = "Living room", content = BitmapUtils.create(100,100), thumbnailContent = BitmapUtils.create(100,100), order = 1)
            )
        )

        val propertyId = propertyRepository.insert(property)
        assertTrue(propertyId > 0)

        val commodityList = commodityDAO.getAll()

        val idsList: MutableList<Long> = mutableListOf()
        for (commodity in commodityList){
            idsList.add(commodity.id!!)
        }
        val searchedProperties = propertyDAO.search(null,
            null,
            null,
            null,
            null,
            null,
            null,
            true,
            idsList)
        assertNotNull(searchedProperties)

    }*/
}