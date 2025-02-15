package com.example.realestatemanager.ui.screen.showproperty

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realestatemanager.domain.usecase.property.GetFullPropertyUseCase
import com.example.realestatemanager.ui.form.formater.DateFormater
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import javax.inject.Inject

@HiltViewModel
class ShowPropertyViewModel @Inject constructor (
    private val getFullPropertyUseCase: GetFullPropertyUseCase,
    private val dateFormater: DateFormater
):ViewModel(){

    private val _uiState = MutableStateFlow<ShowPropertyUIState>(ShowPropertyUIState.IsLoading(
        true
    ))

    val uiState : StateFlow<ShowPropertyUIState> = _uiState


    fun loadProperty(propertyId : Long){
        Log.d("SPS_VIEWMODEL", "Loading property")

        viewModelScope.launch {
            try {
                val property = getFullPropertyUseCase.invoke(propertyId)

                _uiState.value = ShowPropertyUIState.Success(property!!)

            } catch (e : Exception){
                _uiState.value = ShowPropertyUIState.Error(e.message)
            }
        }

    }

    fun format(instant: Instant, pattern : String):String{
        val formattedInstant = dateFormater.format(instant, pattern)
        return formattedInstant
    }
}