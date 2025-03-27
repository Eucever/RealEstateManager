package com.example.realestatemanager.ui.screen.showproperty


import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.realestatemanager.ui.component.PropertyDetail

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