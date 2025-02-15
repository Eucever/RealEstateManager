package com.example.realestatemanager.ui.screen.allproperties

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realestatemanager.domain.model.Property
import com.example.realestatemanager.domain.usecase.property.DeletePropertyUseCase
import com.example.realestatemanager.domain.usecase.property.SearchPropertiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.threeten.bp.Instant
import javax.inject.Inject

@HiltViewModel
class AllPropertiesViewModel @Inject constructor(
    private val searchPropertiesUseCase: SearchPropertiesUseCase,
    private val deletePropertyUseCase: DeletePropertyUseCase
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

    val uiState : StateFlow<AllPropertiesUIState> =_uiState

    init {
        showSearchedProperties()
    }

    private fun getSearchProperties(): AllPropertiesUIState.SearchProperties{
        return when(val currentState = _uiState.value){
            is AllPropertiesUIState.IsLoading -> currentState.searchProperties
            is AllPropertiesUIState.Error -> currentState.searchProperties
            is AllPropertiesUIState.Success ->currentState.searchProperties
            is AllPropertiesUIState.SearchProperties -> currentState
        }
    }

    private fun showSearchedProperties(){
        val searchProperties = getSearchProperties()

        _uiState.value = AllPropertiesUIState.IsLoading(searchProperties)

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
            showSearchedProperties()
        }catch (e: Exception){
            _uiState.value = AllPropertiesUIState.Error(e.message, searchProperties)
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
            showSearchedProperties()
        }catch (e: Exception){
            _uiState.value = AllPropertiesUIState.Error(e.message, searchProperties)
        }
    }
}