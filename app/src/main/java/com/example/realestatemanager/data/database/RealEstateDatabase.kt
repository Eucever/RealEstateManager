package com.example.realestatemanager.data.database

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.realestatemanager.data.converter.BitmapConverter
import com.example.realestatemanager.data.converter.InstantConverter
import com.example.realestatemanager.data.converter.ListConverter
import com.example.realestatemanager.data.dao.AgentDAO
import com.example.realestatemanager.data.dao.CommodityDAO
import com.example.realestatemanager.data.dao.CommodityPropertyCrossRefDAO
import com.example.realestatemanager.data.dao.EstateTypeDAO
import com.example.realestatemanager.data.dao.FullPropertyDAO
import com.example.realestatemanager.data.dao.LocationDAO
import com.example.realestatemanager.data.dao.PictureDAO
import com.example.realestatemanager.data.dao.PropertyDAO
import com.example.realestatemanager.data.entity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Database(entities = [AgentDTO::class,
    CommodityDTO::class,
    PictureDTO::class,
    LocationDTO::class,
    PropertyDTO::class,
    EstateTypeDTO::class,
    CommodityPropertyCrossRefDTO::class], version = 1)
@TypeConverters(BitmapConverter::class, InstantConverter::class, ListConverter::class)
abstract class RealEstateDatabase : RoomDatabase() {
    abstract fun agentDao(): AgentDAO
    abstract fun commodityDao() : CommodityDAO
    abstract fun pictureDao() : PictureDAO
    abstract fun locationDao() : LocationDAO
    abstract fun propertyDao() : PropertyDAO
    abstract fun commodityPropertyCrossRefDao() : CommodityPropertyCrossRefDAO
    abstract fun estateTypeDao() : EstateTypeDAO
    abstract fun fullPropertyDao(): FullPropertyDAO


    private class RealEstateDatabaseCallback(private val scope: CoroutineScope): Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Log.d(TAG, "onCreate called")
            INSTANCE?.let { database ->
                scope.launch {
                    initDatabase(
                        database.agentDao(),
                        database.commodityDao(),
                        database.estateTypeDao(),
                        database.locationDao(),
                        database.pictureDao(),
                        database.commodityPropertyCrossRefDao(),
                        database.propertyDao(),
                    )
                }
            }


        }
        suspend fun initDatabase(
            agentDao : AgentDAO,
            commodityDAO: CommodityDAO,
            estateTypeDAO: EstateTypeDAO,
            locationDAO: LocationDAO,
            pictureDAO: PictureDAO,
            commodityPropertyCrossRefDAO: CommodityPropertyCrossRefDAO,
            propertyDAO: PropertyDAO,
        ) {

            Log.d(TAG, "initializing Database")

            // Agents
            val idAgent1 = agentDao.insert(
                AgentDTO(id = 1, firstName = "John", lastName = "Doe", email = "john.doe@email.com", phone = "123456789")
            )

            Log.d(TAG, "Agent 1 inserted with id $idAgent1")

            val idAgent2 = agentDao.insert(
                AgentDTO(id = 2, firstName = "Jane", lastName = "Doe", email = "jane.doe@email.com", phone = "987654321")
            )

            Log.d(TAG, "Agent 2 inserted with id $idAgent2")

            // Estate types
            val idEstateType1 = estateTypeDAO.insert(
                EstateTypeDTO(id = 1, name = "Apartment", description = "A residential unit located within a building that typically contains multiple floors")
            )

            Log.d(TAG, "EstateType 1 inserted with id $idEstateType1")

            val idEstateType2 = estateTypeDAO.insert(
                EstateTypeDTO(id = 2, name = "House", description = "A standalone residential property, often with its own yard and land")
            )

            Log.d(TAG, "EstateType 2 inserted with id $idEstateType2")

            val idEstateType3 = estateTypeDAO.insert(
                EstateTypeDTO(id = 3 , name = "Office Building", description = "A building primarily used for commercial purposes, housing office spaces")
            )

            Log.d(TAG, "EstateType 3 inserted with id $idEstateType3")

            val idEstateType4 = estateTypeDAO.insert(
                EstateTypeDTO(id = 4, name = "Commercial Property", description = "A property designed for business purposes, such as retail or services")
            )

            Log.d(TAG, "EstateType 4 inserted with id $idEstateType4")

            val idEstateType5 = estateTypeDAO.insert(
                EstateTypeDTO(id = 5, name = "Land", description = "A parcel of land, typically undeveloped and used for various purposes.")
            )

            Log.d(TAG, "EstateType 5 inserted with id $idEstateType5")

            val idEstateType6 = estateTypeDAO.insert(
                EstateTypeDTO(id = 6, name = "Parking / Garage", description = "A space designated for vehicle parking or storage.")
            )

            Log.d(TAG, "EstateType 6 inserted with id $idEstateType6")

            val idEstateType7 = estateTypeDAO.insert(
                EstateTypeDTO(id = 7, name = "Castle / Luxury Estate", description = "A large, high-value property often associated with historical significance or luxury")
            )

            Log.d(TAG, "EstateType 7 inserted with id $idEstateType7")

            val idEstateType8 = estateTypeDAO.insert(
                EstateTypeDTO(id = 8, name = "Rural Property", description = "A property located in rural areas, usually offering large plots of land.")
            )

            Log.d(TAG, "EstateType 8 inserted with id $idEstateType8")

            val idEstateType9 = estateTypeDAO.insert(
                EstateTypeDTO(id = 9, name = "Student Housing", description = "Properties designed specifically for students, often with smaller units and shared amenities")
            )

            Log.d(TAG, "EstateType 9 inserted with id $idEstateType9")

            val idEstateType10 = estateTypeDAO.insert(
                EstateTypeDTO(id = 10, name = "Senior Living / Retirement Community", description = "Properties tailored for senior citizens, typically offering specialized services and amenities.")
            )

            Log.d(TAG, "EstateType 10 inserted with id $idEstateType10")

            val idEstateType11 = estateTypeDAO.insert(
                EstateTypeDTO(id = 11, name = "Residential Building", description = "A multi-unit building primarily used for living purposes.")
            )

            Log.d(TAG, "EstateType 11 inserted with id $idEstateType11")

            val idEstateType12 = estateTypeDAO.insert(
                EstateTypeDTO(id = 12, name = "Industrial Property", description = "Properties used for manufacturing, production, or storage purposes.")
            )

            Log.d(TAG, "EstateType 12 inserted with id $idEstateType12")

            val idEstateType13 = estateTypeDAO.insert(
                EstateTypeDTO(id = 13, name = "Vacation Home", description = "A secondary home used for recreational purposes, often in tourist areas.")
            )

            Log.d(TAG, "EstateType 13 inserted with id $idEstateType13")

            val idEstateType14 = estateTypeDAO.insert(
                EstateTypeDTO(id = 14, name = "Mixed-Use Property", description = "A property designed to accommodate both residential and commercial uses.")
            )

            Log.d(TAG, "EstateType 14 inserted with id $idEstateType14")

            val idEstateType15 = estateTypeDAO.insert(
                EstateTypeDTO(id = 15, name = "Mobile Home / Trailer", description = "A portable dwelling typically located in a mobile home park or on private land.")
            )

            // Commodities

            Log.d(TAG, "EstateType 15 inserted with id $idEstateType15")
            val idCommodity1 = commodityDAO.insert(
                CommodityDTO(id = 1, type = "Shop")
            )
            Log.d(TAG, "Commodity 1 inserted with id $idCommodity1")

            val idCommodity2 = commodityDAO.insert(
                CommodityDTO(id = 2, type = "Park")
            )
            Log.d(TAG, "Commodity 2 inserted with id $idCommodity2")

            val idCommodity3 = commodityDAO.insert(
                CommodityDTO(id = 3, type = "Highway")
            )
            Log.d(TAG, "Commodity 3 inserted with id $idCommodity3")

            val idCommodity4 = commodityDAO.insert(
                CommodityDTO(id = 4, type = "School")
            )
            Log.d(TAG, "Commodity 4 inserted with id $idCommodity4")

            val idCommodity5 = commodityDAO.insert(
                CommodityDTO(id = 5, type = "Hospital")
            )
            Log.d(TAG, "Commodity 5 inserted with id $idCommodity5")

            val idCommodity6 = commodityDAO.insert(
                CommodityDTO(id = 6, type = "Supermarket")
            )
            Log.d(TAG, "Commodity 6 inserted with id $idCommodity6")

            val idCommodity7 = commodityDAO.insert(
                CommodityDTO(id = 7, type = "Gym")
            )
            Log.d(TAG, "Commodity 7 inserted with id $idCommodity7")

            val idCommodity8 = commodityDAO.insert(
                CommodityDTO(id = 8, type = "Restaurant")
            )
            Log.d(TAG, "Commodity 8 inserted with id $idCommodity8")

            val idCommodity9 = commodityDAO.insert(
                CommodityDTO(id = 9, type = "Cinema")
            )
            Log.d(TAG, "Commodity 9 inserted with id $idCommodity9")

            val idCommodity10 = commodityDAO.insert(
                CommodityDTO(id = 10, type = "Public Transport")
            )
            Log.d(TAG, "Commodity 10 inserted with id $idCommodity10")

            val idCommodity11 = commodityDAO.insert(
                CommodityDTO(id = 11, type = "Library")
            )
            Log.d(TAG, "Commodity 11 inserted with id $idCommodity11")

            val idCommodity12 = commodityDAO.insert(
                CommodityDTO(id = 12, type = "University")
            )
            Log.d(TAG, "Commodity 12 inserted with id $idCommodity12")

            val idCommodity13 = commodityDAO.insert(
                CommodityDTO(id = 13, type = "Post Office")
            )
            Log.d(TAG, "Commodity 13 inserted with id $idCommodity13")

            val idCommodity14 = commodityDAO.insert(
                CommodityDTO(id = 14, type = "Pharmacy")
            )
            Log.d(TAG, "Commodity 14 inserted with id $idCommodity14")

            val idCommodity15 = commodityDAO.insert(
                CommodityDTO(id = 15, type = "Airport")
            )
            Log.d(TAG, "Commodity 15 inserted with id $idCommodity15")

        }
    }

    companion object{
        @Volatile
        private var INSTANCE: RealEstateDatabase? = null
        private const val DATABASE_NAME = "RealEstateManagerDb"
        fun getDatabase(c : Context, cc: CoroutineScope) : RealEstateDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    c.applicationContext,
                    RealEstateDatabase::class.java,
                    DATABASE_NAME
                ).addCallback(RealEstateDatabaseCallback(cc)).build()
                INSTANCE=instance
                instance
            }
        }
    }
}