package com.example.realestatemanager.ui.screen.showproperty

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.realestatemanager.domain.model.Agent
import com.example.realestatemanager.domain.model.Location
import com.example.realestatemanager.domain.model.Property
import com.example.realestatemanager.ui.component.ImageSection
import com.example.realestatemanager.ui.component.PropertyDetail
import com.example.realestatemanager.ui.component.RoundedTextField
import org.threeten.bp.Instant

@Composable
fun ShowPropertyScreen(
    propertyId: Long,
    viewModel: ShowPropertyViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsState()

    viewModel.loadProperty(propertyId)

    LazyColumn (modifier = Modifier.padding(8.dp)){
        when(uiState){
            is ShowPropertyUIState.IsLoading ->{
                item {
                    Text("Loading... Please Wait")
                }
            }
            is ShowPropertyUIState.Error -> {
                item {
                    Text("Error: ${(uiState as ShowPropertyUIState.Error).sError}")
                }

            }
            is ShowPropertyUIState.Success ->{
                item {
                    PropertyDetail(property = (uiState as ShowPropertyUIState.Success).property, onFormatDate = {
                        i, s -> viewModel.format(i, s)
                    })
                }
            }
        }
    }




}