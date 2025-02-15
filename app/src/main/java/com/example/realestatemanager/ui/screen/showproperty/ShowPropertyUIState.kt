package com.example.realestatemanager.ui.screen.showproperty

import com.example.realestatemanager.domain.model.Property

sealed class ShowPropertyUIState {
    data class IsLoading(
        val status: Boolean,
    ) : ShowPropertyUIState()

    data class Error(
        val sError: String?,
    ) : ShowPropertyUIState()

    data class Success(
        val property: Property,
    ) : ShowPropertyUIState()
}