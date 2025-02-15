package com.example.realestatemanager

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.realestatemanager.data.dao.LocationDAO
import com.example.realestatemanager.data.database.RealEstateDatabase
import com.example.realestatemanager.data.repository.LocationRepository
import com.example.realestatemanager.domain.model.Agent
import com.example.realestatemanager.domain.model.Location
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
class LocationRepositoryTest {
    @Inject
    lateinit var locationDAO: LocationDAO
    private lateinit var locationRepository: LocationRepository
    private lateinit var roomDatabase: RealEstateDatabase

    @Before
    fun setup(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        roomDatabase = Room.inMemoryDatabaseBuilder(context, RealEstateDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        locationDAO = roomDatabase.locationDao()
        locationRepository = LocationRepository(locationDAO)
    }

    @After
    fun teardown(){
        roomDatabase.close()
    }

    @Test
    fun insertGetLocation() = runTest{
        val location = Location(id = null, street = "Rue de la rue", streetNumber = 1, postalCode = "00000", city = "Paris", country = "France", latitude = 1.0, longitude = 1.0)
        val id = locationRepository.insert(location)

        assertTrue(id > 0)
        val agentInDb = locationRepository.getById(id).first()

        assertNotNull(agentInDb)
        assertEquals(location.street, agentInDb?.street)
        assertEquals(location.street, agentInDb?.street)
    }

    @Test
    fun updateLocation() = runTest {
        val location = Location(id = null, street = "Rue de la rue", streetNumber = 1, postalCode = "00000", city = "Paris", country = "France", latitude = 1.0, longitude = 1.0)
        val id = locationRepository.insert(location)
        val locationUpdated = location.copy(id = id, street = "Boulevard de la rue")

        val rowsUpdated = locationRepository.update(locationUpdated)
        assertEquals(1, rowsUpdated)

        val locationFromDb = locationRepository.getById(id).first()
        assertNotNull(locationFromDb)
        assertEquals(locationUpdated.street, locationFromDb?.street)
    }

    @Test
    fun deleteLocation() = runTest {
        val location = Location(id = null, street = "Rue de la rue", streetNumber = 1, postalCode = "00000", city = "Paris", country = "France", latitude = 1.0, longitude = 1.0)
        val id = locationRepository.insert(location)

        locationRepository.deleteById(id)

        val locationFromDb = locationRepository.getById(id).first()
        assertNull(locationFromDb)
    }

    @Test
    fun insertGetLocationsList() = runTest {
        val locations = listOf(
            Location(id = null, street = "Rue de la rue", streetNumber = 1, postalCode = "00000", city = "Paris", country = "France", latitude = 1.0, longitude = 1.0),
            Location(id = null, street = "Boulevard de la rue", streetNumber = 2, postalCode = "11111", city = "Londre", country = "Angleterre", latitude = 2.0, longitude = 2.0)
        )

        val ids = locationRepository.insert(locations)
        assertTrue(ids.size == locations.size)

        val allLocations = locationRepository.getAll().first()
        assertEquals(locations.size, allLocations.size)
    }

    @Test
    fun deleteAllLocations() = runTest {
        val locations = listOf(
            Location(id = null, street = "Rue de la rue", streetNumber = 1, postalCode = "00000", city = "Paris", country = "France", latitude = 1.0, longitude = 1.0),
            Location(id = null, street = "Boulevard de la rue", streetNumber = 2, postalCode = "11111", city = "Londre", country = "Angleterre", latitude = 2.0, longitude = 2.0)
        )

        val ids = locationRepository.insert(locations)
        assertTrue(ids.size == locations.size)
        locationRepository.deleteAll()

        val allLocations = locationRepository.getAll().first()
        assertTrue(allLocations.isEmpty())
    }

    @Test
    fun getAllLocations()= runTest {
        val locations = listOf(
            Location(id = null, street = "Rue de la rue", streetNumber = 1, postalCode = "00000", city = "Paris", country = "France", latitude = 1.0, longitude = 1.0),
            Location(id = null, street = "Boulevard de la rue", streetNumber = 2, postalCode = "11111", city = "Londre", country = "Angleterre", latitude = 2.0, longitude = 2.0)
        )

        locationRepository.insert(locations)

        val locationsFlow = locationRepository.getAll().first()
        assertEquals(locations.size, locationsFlow.size)

    }
}