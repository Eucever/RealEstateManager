package com.example.realestatemanager.di

import android.content.Context
import com.example.realestatemanager.AppSingleton
import com.example.realestatemanager.data.dao.AgentDAO
import com.example.realestatemanager.data.dao.CommodityDAO
import com.example.realestatemanager.data.dao.CommodityPropertyCrossRefDAO
import com.example.realestatemanager.data.dao.EstateTypeDAO
import com.example.realestatemanager.data.dao.PictureDAO
import com.example.realestatemanager.data.dao.PropertyDAO
import com.example.realestatemanager.data.database.RealEstateDatabase
import com.example.realestatemanager.data.repository.AgentRepository
import com.example.realestatemanager.data.repository.CommodityRepository
import com.example.realestatemanager.data.repository.EasyPropertyRepository
import com.example.realestatemanager.data.repository.EstateTypeRepository
import com.example.realestatemanager.data.repository.GPSRepository
import com.example.realestatemanager.data.repository.PropertyRepository
import com.example.realestatemanager.domain.usecase.agent.GetAllAgentsUseCase
import com.example.realestatemanager.domain.usecase.commodity.GetAllCommoditiesUseCase
import com.example.realestatemanager.domain.usecase.estatetype.GetAllEstateTypesUseCase
import com.example.realestatemanager.domain.usecase.loancalculator.LoanCalculatorUseCase
import com.example.realestatemanager.domain.usecase.property.InsertPropertyUseCase
import com.example.realestatemanager.domain.usecase.property.SearchPropertiesUseCase
import com.example.realestatemanager.domain.usecase.property.UpdatePropertyUseCase
import com.example.realestatemanager.ui.form.converter.FormConverter
import com.example.realestatemanager.ui.form.converter.ListConverter
import com.example.realestatemanager.ui.form.formater.DateFormater
import com.example.realestatemanager.ui.form.formater.FormFormater
import com.example.realestatemanager.ui.form.validator.FormValidator
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    //DATABASE

    @Provides
    @Singleton
    // database coroutines scope for initDatabase operations
    fun provideCoroutineScope(): CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        coroutineScope: CoroutineScope
    ): RealEstateDatabase {
        return RealEstateDatabase.getDatabase(context, coroutineScope)
    }

    //DAO
    @Provides
    fun provideAgentDAO(appDatabase: RealEstateDatabase): AgentDAO {
        return appDatabase.agentDao()
    }

    @Provides
    fun provideCommodityDAO(appDatabase: RealEstateDatabase): CommodityDAO {
        return appDatabase.commodityDao()
    }

    @Provides
    fun provideEstateTypeDAO(appDatabase: RealEstateDatabase): EstateTypeDAO {
        return appDatabase.estateTypeDao()
    }

    @Provides
    fun provideLocationDAO(appDatabase: RealEstateDatabase) = appDatabase.locationDao()

    @Provides
    fun providePictureDAO(appDatabase: RealEstateDatabase): PictureDAO {
        return appDatabase.pictureDao()
    }

    @Provides
    fun provideCommodityPropertyCrossRefDAO(appDatabase: RealEstateDatabase): CommodityPropertyCrossRefDAO {
        return appDatabase.commodityPropertyCrossRefDao()
    }

    @Provides
    fun providePropertyDAO(appDatabase: RealEstateDatabase): PropertyDAO {
        return appDatabase.propertyDao()
    }

    @Provides
    fun provideFullPropertyDAO(appDatabase: RealEstateDatabase) = appDatabase.fullPropertyDao()

    //USECASE
    @Provides
    fun provideGetAllAgentsUseCase(agentRepository: AgentRepository) = GetAllAgentsUseCase(agentRepository)

    @Provides
    fun provideSearchPropertiesUseCase(propertyRepository : PropertyRepository) = SearchPropertiesUseCase(propertyRepository)

    @Provides
    fun provideInsertPropertyUseCase(easyPropertyRepository: EasyPropertyRepository) = InsertPropertyUseCase(easyPropertyRepository)

    @Provides
    fun provideLoanCalculatorUseCase() = LoanCalculatorUseCase()

    @Provides
    fun provideGetAllCommoditiesUseCase(commodityRepository: CommodityRepository) = GetAllCommoditiesUseCase(commodityRepository)

    @Provides
    fun provideEstateTypesUseCase(estateTypeRepository: EstateTypeRepository) = GetAllEstateTypesUseCase(estateTypeRepository)

    @Provides
    fun provideUpdatePropertyUseCase(easyPropertyRepository: EasyPropertyRepository) = UpdatePropertyUseCase(easyPropertyRepository)


    @Provides
    fun provideFormValidator() = FormValidator()

    @Provides
    fun provideFormConverter() = FormConverter()

    @Provides
    fun provideFormFormater() = FormFormater()

    @Provides
    fun provideDateFormater() = DateFormater()

    @Provides
    fun provideListConvert()= ListConverter()

    @Provides
    fun provideGPSRepository()= GPSRepository()

}