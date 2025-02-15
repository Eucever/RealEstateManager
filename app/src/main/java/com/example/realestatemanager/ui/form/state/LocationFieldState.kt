package com.example.realestatemanager.ui.form.state

data class LocationFormState(
    val street: FieldState,
    val streetNumber: FieldState,
    val postalCode: FieldState,
    val city: FieldState,
    val country: FieldState,
    val longitude: FieldState,
    val latitude: FieldState
)