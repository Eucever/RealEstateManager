package com.example.realestatemanager.ui.screen.allproperties

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.query
import com.example.realestatemanager.data.repository.GPSRepository
import com.example.realestatemanager.domain.model.Property
import com.example.realestatemanager.domain.model.gps.GPSStatus
import com.example.realestatemanager.domain.usecase.property.DeletePropertyUseCase
import com.example.realestatemanager.domain.usecase.property.GetFullPropertyUseCase
import com.example.realestatemanager.domain.usecase.property.SearchPropertiesUseCase
import com.example.realestatemanager.ui.form.formater.DateFormater
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.threeten.bp.Instant
import javax.inject.Inject

@SuppressLint("MissingPermission")
@HiltViewModel
class AllPropertiesViewModel @Inject constructor(
    private val searchPropertiesUseCase: SearchPropertiesUseCase,
    private val getFullPropertyUseCase: GetFullPropertyUseCase,
    private val deletePropertyUseCase: DeletePropertyUseCase,
    private val dateFormater: DateFormater,
    private val gpsRepository: GPSRepository
) : ViewModel(){
    private val _uiState = MutableStateFlow<AllPropertiesUIState>(AllPropertiesUIState.IsLoading(
        AllPropertiesUIState.SearchProperties(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        )
    ))

    private val hasGPSpermissionLiveData = MutableLiveData<Boolean>()

    private val gpsStatusLiveData = MediatorLiveData<GPSStatus>()

    val uiState : StateFlow<AllPropertiesUIState> =_uiState

    init {
        initProperties()
        viewModelScope.launch {
            val location = gpsRepository.getLastLocation()
            if (location != null){
               updateLocation(location)
            }
        }
        startLocationUpdates()
    }

    private fun updateLocation(location: Location) {
        Log.d("ALLPROPVM", "Location : $location")
        when(val currentState = _uiState.value){
            is AllPropertiesUIState.Success -> {
                Log.d("ALLPROPVM", "Refreshing ui State Location")
                _uiState.value = currentState.copy(
                    propertyLocation = LatLng(location.latitude, location.longitude)
                )
            }
            is AllPropertiesUIState.Error -> {Log.d("ALLPROPVM", "Error")}
            is AllPropertiesUIState.IsLoading -> {Log.d("ALLPROPVM", "Is loading")}
            is AllPropertiesUIState.SearchProperties -> {Log.d("ALLPROPVM", "Search")}
        }
    }

    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        viewModelScope.launch {
            gpsRepository.getLocationUpdate().collect{
                location ->
                updateLocation(location)
            }
        }
    }

    fun getGPSLiveData(): LiveData<GPSStatus> {
        return gpsStatusLiveData
    }
    private fun getState(): AllPropertiesUIState.Success?{
        return when(val currentState = _uiState.value){
            is AllPropertiesUIState.IsLoading -> null
            is AllPropertiesUIState.Error -> null
            is AllPropertiesUIState.Success -> currentState
            else -> throw IllegalStateException("Unknown State")
        }
    }

    private fun getSearchProperties(): AllPropertiesUIState.SearchProperties{
        return when(val currentState = _uiState.value){
            is AllPropertiesUIState.IsLoading -> currentState.searchProperties
            is AllPropertiesUIState.Error -> currentState.searchProperties
            is AllPropertiesUIState.Success ->currentState.searchProperties
            is AllPropertiesUIState.SearchProperties -> currentState
        }
    }


    private fun initProperties(){
        val searchProperties = getSearchProperties()

        _uiState.value = AllPropertiesUIState.IsLoading(searchProperties)

        val currentState = getState()
        viewModelScope.launch {
            try {
                val properties = searchPropertiesUseCase(
                    type = searchProperties.type,
                    minPrice = searchProperties.minPrice,
                    maxPrice = searchProperties.maxPrice,
                    minSurface = searchProperties.minSurface,
                    maxSurface = searchProperties.maxSurface,
                    minNbRooms = searchProperties.minNbRooms,
                    maxNbRooms = searchProperties.maxNbRooms,
                    isAvailable = searchProperties.isAvailable,
                    commodities = searchProperties.commodities,
                    dateSale = searchProperties.dateSale
                )

                _uiState.value = AllPropertiesUIState.Success(properties, searchProperties)
            }catch (e: Exception){
                _uiState.value = AllPropertiesUIState.Error(e.message, searchProperties)
            }
        }
    }

    fun deleteProperty(property: Property){
        viewModelScope.launch {
            val searchProperties = getSearchProperties()

            _uiState.value = AllPropertiesUIState.IsLoading(searchProperties)
            try {
                deletePropertyUseCase(property)
                val properties = searchPropertiesUseCase(
                    type = searchProperties.type,
                    minPrice = searchProperties.minPrice,
                    maxPrice = searchProperties.maxPrice,
                    minSurface = searchProperties.minSurface,
                    maxSurface = searchProperties.maxSurface,
                    minNbRooms = searchProperties.minNbRooms,
                    maxNbRooms = searchProperties.maxNbRooms,
                    isAvailable = searchProperties.isAvailable,
                    commodities = searchProperties.commodities,
                    dateSale = searchProperties.dateSale
                )
                _uiState.value = AllPropertiesUIState.Success(properties, searchProperties)
            }catch (e: Exception){
                _uiState.value = AllPropertiesUIState.Error(e.message, searchProperties)
            }
        }
    }

    fun searchPropertyWithArgs(type: Long?,
                               minPrice : Double?,
                               maxPrice : Double?,
                               minSurface: Double?,
                               maxSurface: Double?,
                               minNbRooms : Int?,
                               maxNbRooms : Int?,
                               isAvailable : Boolean?,
                               commodities : List<Long>?,
                               dateSale :Instant?
                               ){
        val searchProperties = getSearchProperties()


        _uiState.value = AllPropertiesUIState.IsLoading(searchProperties)

        try {
            _uiState.value = AllPropertiesUIState.SearchProperties(
                type = type,
                minPrice = minPrice,
                maxPrice = maxPrice,
                minSurface = minSurface,
                maxSurface = maxSurface,
                minNbRooms = minNbRooms,
                maxNbRooms = maxNbRooms,
                isAvailable = isAvailable,
                commodities = commodities,
                dateSale = dateSale
            )
            initProperties()
        }catch (e: Exception){
            _uiState.value = AllPropertiesUIState.Error(e.message, searchProperties)
        }
    }


    private fun setStatus(location: Location?, hasGPSpermission: Boolean?) {
        Log.d("ALLPROPSCREENVM", "Setstatus $location + $hasGPSpermission")
    if (location == null) {
        if (hasGPSpermission == null || !hasGPSpermission) {
            gpsStatusLiveData.setValue(GPSStatus(hasGpsPermission = false,  querying = false))
        } else {
            gpsStatusLiveData.setValue(GPSStatus(hasGpsPermission = true, querying = true))
        }
        } else {
        gpsStatusLiveData.setValue(GPSStatus(longitude = location.longitude, latitude =  location.latitude))
        }
    }

    fun resetFilters(){
        val searchProperties = getSearchProperties()

        _uiState.value = AllPropertiesUIState.IsLoading(searchProperties)

        try {
            _uiState.value = AllPropertiesUIState.SearchProperties(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
            )
            initProperties()
        }catch (e: Exception){
            _uiState.value = AllPropertiesUIState.Error(e.message, searchProperties)
        }
    }

    fun resetCurrentProperty(){
       val state = getState()

        if (state != null){
            _uiState.value = state.copy(currentProperty = null)
        }
    }

    fun updateCurrentProperty(propertyId: Long){
        val searchProperties = getSearchProperties()

        //_uiState.value = AllPropertiesUIState.IsLoading(searchProperties)
        val currentState = getState()
        viewModelScope.launch {
            try {
                val properties = searchPropertiesUseCase(
                    type = searchProperties.type,
                    minPrice = searchProperties.minPrice,
                    maxPrice = searchProperties.maxPrice,
                    minSurface = searchProperties.minSurface,
                    maxSurface = searchProperties.maxSurface,
                    minNbRooms = searchProperties.minNbRooms,
                    maxNbRooms = searchProperties.maxNbRooms,
                    isAvailable = searchProperties.isAvailable,
                    commodities = searchProperties.commodities,
                    dateSale = searchProperties.dateSale
                )

                val currentProperty = getFullPropertyUseCase.invoke(propertyId)


                if (currentState != null) {
                    _uiState.value = currentState.copy(listProperties = properties, searchProperties = searchProperties, currentProperty = currentProperty)
                }else{
                    _uiState.value = AllPropertiesUIState.Error("State is null", searchProperties)
                }
            }catch (e: Exception){
                _uiState.value = AllPropertiesUIState.Error(e.message, searchProperties)
            }
        }

    }





    fun format(instant: Instant, pattern : String):String{
        val formattedInstant = dateFormater.format(instant, pattern)
        return formattedInstant
    }

    fun goToCurrentPos() {

    }
}