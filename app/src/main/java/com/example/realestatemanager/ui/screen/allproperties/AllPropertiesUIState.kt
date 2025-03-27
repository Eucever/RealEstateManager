package com.example.realestatemanager.ui.screen.allproperties

import com.example.realestatemanager.domain.model.Property
import com.google.android.gms.maps.model.LatLng

import org.threeten.bp.Instant

sealed class AllPropertiesUIState {

    data class IsLoading(
        val searchProperties: SearchProperties
    ) : AllPropertiesUIState()

    data class Success(
        val listProperties : List<Property>,
        val searchProperties: SearchProperties,
        val currentProperty : Property? = null,
        val propertyLatitude : Double? = null,
        val propertyLongitude : Double? = null,
        val propertyLocation : LatLng? = null
    ) : AllPropertiesUIState()

    data class Error(
        val sError: String?,
        val searchProperties: SearchProperties
    ) : AllPropertiesUIState()

    data class SearchProperties(
        val type: Long?,
        val minPrice: Double?,
        val maxPrice: Double?,
        val minSurface: Double?,
        val maxSurface: Double?,
        val minNbRooms: Int?,
        val maxNbRooms: Int?,
        val isAvailable: Boolean?,
        val commodities: List<Long>?,
        val dateSale: Instant?,
    ) : AllPropertiesUIState()
}