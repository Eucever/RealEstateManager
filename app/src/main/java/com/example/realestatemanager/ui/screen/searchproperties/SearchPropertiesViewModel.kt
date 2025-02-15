package com.example.realestatemanager.ui.screen.searchproperties

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realestatemanager.domain.model.Commodity
import com.example.realestatemanager.domain.model.EstateType
import com.example.realestatemanager.domain.usecase.commodity.GetAllCommoditiesUseCase
import com.example.realestatemanager.domain.usecase.estatetype.GetAllEstateTypesUseCase
import com.example.realestatemanager.domain.usecase.property.SearchPropertiesUseCase
import com.example.realestatemanager.ui.form.converter.FormConverter
import com.example.realestatemanager.ui.form.converter.ListConverter
import com.example.realestatemanager.ui.form.state.FieldState
import com.example.realestatemanager.ui.form.state.InstantFieldState
import com.example.realestatemanager.ui.form.validator.FormValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.threeten.bp.Instant
import javax.inject.Inject

@HiltViewModel
class SearchPropertiesViewModel @Inject constructor(
    private val searchPropertiesUseCase: SearchPropertiesUseCase,
    private val getAllEstateTypesUseCase: GetAllEstateTypesUseCase,
    private val getAllCommoditiesUseCase: GetAllCommoditiesUseCase,
    private val formValidator : FormValidator,
    private val formConverter : FormConverter,
    private val listConverter: ListConverter
):ViewModel(){
    companion object {
        private const val TAG = "SearchPropertiesViewModel"

        private const val PRICE_ALLOW_IS_REQUIRED = false
        private const val PRICE_MIN = 1.0
        private const val PRICE_MAX = 10000000.0

        private const val SURFACE_IS_REQUIRED = false
        private const val SURFACE_MIN = 1.0
        private const val SURFACE_MAX = 10000.0

        private const val NB_ROOMS_IS_REQUIRED = false
        private const val NB_ROOMS_MIN = 1
        private const val NB_ROOMS_MAX = 100

        private const val SALE_DATE_IS_REQUIRED = false
        private val SALE_DATE_MIN : Instant? = null
        private val SALE_DATE_MAX : Instant? = null
    }

    private val _uiState = MutableStateFlow<SearchPropertiesUIState>(
        SearchPropertiesUIState.FormState(
            minPrice = FieldState("", true),
            maxPrice = FieldState("", true),
            minSurface = FieldState("", true),
            maxSurface = FieldState("", true),
            maxNbRooms = FieldState("", true),
            minNbRooms = FieldState("", true),
            dateSale = InstantFieldState(null, false),
            isAvailable = true,
            isFormValid = false,

            )
    )


    val uiState : StateFlow<SearchPropertiesUIState> = _uiState

    init {
        loadData()
    }

    private fun loadData(){
            viewModelScope.launch {
                val estateType = getAllEstateTypesUseCase()
                val commodities = getAllCommoditiesUseCase()

                if (estateType.isSuccess){
                    val currentState = getFormState()
                    _uiState.value = currentState.copy(
                        allEstateTypes = estateType.getOrNull() ?: emptyList(),
                        allCommodities = commodities.getOrNull() ?: emptyList()
                    )
                }else{
                    _uiState.value = SearchPropertiesUIState.Error(sError = "Unable to fetch date", formState = getFormState())
                }
            }
    }


    private fun getFormState(): SearchPropertiesUIState.FormState{
        return when(val currentState = _uiState.value){
            is SearchPropertiesUIState.IsLoading -> currentState.formState
            is SearchPropertiesUIState.Error -> currentState.formState
            is SearchPropertiesUIState.FormState -> currentState
            else -> throw IllegalStateException("Unknown State")
        }
    }

    fun searchProperties(){
        viewModelScope.launch {

            val currentState = getFormState()

            _uiState.value = SearchPropertiesUIState.IsLoading(currentState)
                try {
                    val properties = searchPropertiesUseCase(
                        type = currentState.selectedEstateType?.id,
                        minPrice = formConverter.toDouble(currentState.minPrice.value),
                        maxPrice = formConverter.toDouble(currentState.maxPrice.value),
                        minSurface = formConverter.toDouble(currentState.minSurface.value),
                        maxSurface = formConverter.toDouble(currentState.maxSurface.value),
                        minNbRooms = formConverter.toInt(currentState.minNbRooms.value),
                        maxNbRooms = formConverter.toInt(currentState.maxNbRooms.value),
                        isAvailable = currentState.isAvailable,
                        commodities = currentState.commoditiesForQuery,
                        dateSale = currentState.dateSale.value
                    )

                    _uiState.value = currentState.copy(listProperties = properties)
                }catch (e: Exception){
                    _uiState.value = SearchPropertiesUIState.Error(e.message, currentState)
                }

        }
    }

    fun updateFieldValue(fieldName : String, newValue : String){
        val currentState = getFormState()
        val updatedState = when(fieldName){
            "minPrice" -> currentState.copy(minPrice = formValidator.validateMinDouble(newValue, PRICE_MIN, PRICE_MAX, PRICE_ALLOW_IS_REQUIRED, currentState.maxPrice.value))
            "maxPrice" -> currentState.copy(maxPrice = formValidator.validateMaxDouble(newValue, PRICE_MIN, PRICE_MAX, PRICE_ALLOW_IS_REQUIRED, currentState.minPrice.value))
            "minSurface" -> currentState.copy(minSurface = formValidator.validateMinDouble(newValue, SURFACE_MIN, SURFACE_MAX, SURFACE_IS_REQUIRED, currentState.maxSurface.value))
            "maxSurface" -> currentState.copy(maxSurface = formValidator.validateMaxDouble(newValue, SURFACE_MIN, SURFACE_MAX, SURFACE_IS_REQUIRED, currentState.minSurface.value))
            "minNbRooms" -> currentState.copy(minNbRooms = formValidator.validateMinInteger(newValue, NB_ROOMS_MIN, NB_ROOMS_MAX, NB_ROOMS_IS_REQUIRED, currentState.maxNbRooms.value))
            "maxNbRooms" -> currentState.copy(maxNbRooms = formValidator.validateMaxInteger(newValue, NB_ROOMS_MIN, NB_ROOMS_MAX, NB_ROOMS_IS_REQUIRED, currentState.minNbRooms.value))
            else -> throw IllegalArgumentException("Unknown Field Name")
        }
        val isFormValid = isFormValid(updatedState)
        _uiState.value = updatedState.copy(isFormValid = isFormValid)
    }

    fun updateFieldValue(fieldName: String, instant: Instant?){
        viewModelScope.launch {
            val currentState = getFormState()
            val updatedState = when(fieldName){
                "dateSale" -> currentState.copy(dateSale = formValidator.validateInstant(instant, SALE_DATE_MIN, SALE_DATE_MAX, SALE_DATE_IS_REQUIRED))
                else -> throw IllegalArgumentException("Unknown Field name")
            }
            val isFormValid = isFormValid(updatedState)
            _uiState.value = updatedState.copy(isFormValid= isFormValid)
        }
    }

    fun updateSelectedEstateType(it: EstateType) {
        viewModelScope.launch {
            var currentState = getFormState()
            currentState = currentState.copy(selectedEstateType = it)
            val isFormValid = isFormValid(currentState)
            _uiState.value = currentState.copy(isFormValid = isFormValid)
        }
    }

    fun updateSelectedAvailable(it: Boolean) {
        viewModelScope.launch {
            var currentState = getFormState()
            currentState = currentState.copy(isAvailable = it)
            val isFormValid = isFormValid(currentState)
            _uiState.value = currentState.copy(isFormValid = isFormValid)
        }
    }

    fun updateSelectedCommodities(it: List<Commodity>) {
        viewModelScope.launch {
            var currentState = getFormState()
            currentState = currentState.copy(selectedCommodities = it)
            currentState = currentState.copy(commoditiesForQuery = listConverter.toCommoditiesToIdList(it))
            val isFormValid = isFormValid(currentState)
            _uiState.value = currentState.copy(isFormValid = isFormValid)
        }
    }

    private fun isFormValid(updatedState: SearchPropertiesUIState.FormState): Boolean {
        Log.d(TAG, "selectedEstateType: ${updatedState.selectedEstateType != null}")
        return updatedState.selectedEstateType != null
                && updatedState.minPrice.isValid
                && updatedState.maxPrice.isValid
    }

    fun resetDateSale(){
        viewModelScope.launch {
            var currentState = getFormState()
            currentState = currentState.copy(dateSale = InstantFieldState(null, true))
            val  isFormValid = isFormValid(currentState)
            _uiState.value = currentState.copy(isFormValid = isFormValid)
        }
    }


}