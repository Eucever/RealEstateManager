package com.example.realestatemanager.ui.screen.searchproperties

import com.example.realestatemanager.domain.model.Commodity
import com.example.realestatemanager.domain.model.EstateType
import com.example.realestatemanager.domain.model.Property
import com.example.realestatemanager.ui.form.state.FieldState
import com.example.realestatemanager.ui.form.state.InstantFieldState
import com.example.realestatemanager.ui.screen.allproperties.AllPropertiesUIState

sealed class SearchPropertiesUIState {
    data class IsLoading(
        val formState: FormState
    ) : SearchPropertiesUIState()

    data class Success(
        val listProperties : List<Property>,
    ) : SearchPropertiesUIState()

    data class Error(
        val sError: String?,
        val formState: FormState
    ) : SearchPropertiesUIState()

    data class FormState(
        val selectedEstateType: EstateType ?= null,

        val minPrice: FieldState,
        val maxPrice: FieldState,
        val minSurface : FieldState,
        val maxSurface: FieldState,
        val minNbRooms: FieldState,
        val maxNbRooms: FieldState,
        val isAvailable : Boolean,
        val commoditiesForQuery : List<Long> ?= null,
        val dateSale : InstantFieldState,


        val selectedCommodities: List<Commodity> = emptyList(),
        val listProperties : List<Property> = emptyList(),
        val allEstateTypes: List<EstateType> = emptyList(),
        val allCommodities: List<Commodity> = emptyList(),

        val isFormValid : Boolean
    ): SearchPropertiesUIState()
}