package com.example.realestatemanager.ui.screen.createproperty

import com.example.realestatemanager.domain.model.Agent
import com.example.realestatemanager.domain.model.Commodity
import com.example.realestatemanager.domain.model.EstateType
import com.example.realestatemanager.domain.model.Picture
import com.example.realestatemanager.ui.form.state.FieldState
import com.example.realestatemanager.ui.form.state.InstantFieldState
import com.example.realestatemanager.ui.form.state.LocationFormState

sealed class CreatePropertyUIState{
    data class IsLoading(
        val formState : FormState
    ):CreatePropertyUIState()

    data class Success(
        val propertyId: Long
    ):CreatePropertyUIState()

    data class Error(
        val sError : String?,
        val formState: FormState
    ):CreatePropertyUIState()

    data class FormState(
        val name: FieldState,
        val description: FieldState,
        val surface : FieldState,
        val nbRooms : FieldState,
        val nbBathrooms : FieldState,
        val nbBedrooms: FieldState,
        val price : FieldState,
        val dateEntry : InstantFieldState,
        val dateSale : InstantFieldState,
        val apartmentNumber: FieldState,
        val selectedAgent: Agent?= null,
        val selectedEstateType: EstateType ?= null,
        val selectedCommodities: List<Commodity> = emptyList(),
        val pictures : List<Picture>,
        val location : LocationFormState,

        val isFormValid: Boolean,

        val allEstateTypes: List<EstateType> = emptyList(),
        val allCommodities: List<Commodity> = emptyList(),
        val allAgents: List<Agent> = emptyList()
    ) : CreatePropertyUIState()
}

