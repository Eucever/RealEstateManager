package com.example.realestatemanager.ui.screen.searchproperties


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.realestatemanager.ui.component.InstantDateSelectionField
import com.example.realestatemanager.ui.component.SelectCommoditiesField
import com.example.realestatemanager.ui.component.SelectEstateTypeField
import com.example.realestatemanager.ui.component.SelectIsAvailable

@Composable
fun SearchPropertiesScreen(navController: NavController, viewModel: SearchPropertiesViewModel = hiltViewModel()) {
    var searchQuery by remember { mutableStateOf("") }

    val uiState = viewModel.uiState.collectAsState().value


    LazyColumn {
        when(uiState){
            is  SearchPropertiesUIState.IsLoading -> {
                item {
                    Text("Loading...Please Wait")
                }
            }
            is SearchPropertiesUIState.Success -> {
                item {
                    Text("SUCESS")
                }
            }
            is SearchPropertiesUIState.Error -> {
                item {
                    Text("Error: ${uiState.sError ?: "Unknown Error"}")
                }
            }
            is SearchPropertiesUIState.FormState -> {
                item {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Search Properties"/*, style = MaterialTheme.typography.h5*/)

                        Spacer(modifier = Modifier.height(16.dp))

                        SelectEstateTypeField(uiState.allEstateTypes, uiState.selectedEstateType){
                            viewModel.updateSelectedEstateType(it)
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = uiState.minPrice.value,
                            onValueChange = { viewModel.updateFieldValue("minPrice", it) },
                            label = { Text("Minimum Price") },
                            modifier = Modifier.fillMaxWidth(),
                            isError = !uiState.minPrice.isValid,
                            supportingText = { uiState.minPrice.errorMessage?.let { Text(it) } }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = uiState.maxPrice.value,
                            onValueChange = { viewModel.updateFieldValue("maxPrice", it) },
                            label = { Text("Maximum Price") },
                            modifier = Modifier.fillMaxWidth(),
                            isError = !uiState.maxPrice.isValid,
                            supportingText = { uiState.maxPrice.errorMessage?.let { Text(it) } }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = uiState.minSurface.value,
                            onValueChange = { viewModel.updateFieldValue("minSurface", it) },
                            label = { Text("Minimum Surface") },
                            modifier = Modifier.fillMaxWidth(),
                            isError = !uiState.minSurface.isValid,
                            supportingText = { uiState.minSurface.errorMessage?.let { Text(it) } }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = uiState.maxSurface.value,
                            onValueChange = { viewModel.updateFieldValue("maxSurface", it) },
                            label = { Text("Maximum Surface") },
                            modifier = Modifier.fillMaxWidth(),
                            isError = !uiState.maxSurface.isValid,
                            supportingText = { uiState.maxSurface.errorMessage?.let { Text(it) } }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = uiState.minNbRooms.value,
                            onValueChange = { viewModel.updateFieldValue("minNbRooms", it) },
                            label = { Text("Minimum Number of Rooms") },
                            modifier = Modifier.fillMaxWidth(),
                            isError = !uiState.minNbRooms.isValid,
                            supportingText = { uiState.minNbRooms.errorMessage?.let { Text(it) } }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = uiState.maxNbRooms.value,
                            onValueChange = { viewModel.updateFieldValue("maxNbRooms", it) },
                            label = { Text("Maximum Number of Rooms") },
                            modifier = Modifier.fillMaxWidth(),
                            isError = !uiState.maxNbRooms.isValid,
                            supportingText = { uiState.maxNbRooms.errorMessage?.let { Text(it) } }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        SelectIsAvailable(uiState.isAvailable) {
                            viewModel.updateSelectedAvailable(it)
                        }

                        when (uiState.isAvailable){
                            false -> {
                                InstantDateSelectionField(
                                    label = "Sale Date",
                                    selectedInstant = uiState.dateSale.value,
                                    onDateSelected = { newInstant ->
                                        viewModel.updateFieldValue("dateSale", newInstant)
                                    }
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                            }true -> {
                                viewModel.resetDateSale()
                            }

                        }

                        SelectCommoditiesField(uiState.allCommodities, uiState.selectedCommodities) {
                            viewModel.updateSelectedCommodities(it)
                        }

                        Button(
                            onClick = {
                                navController.currentBackStackEntry?.savedStateHandle?.set("type", uiState.selectedEstateType?.id)
                                navController.currentBackStackEntry?.savedStateHandle?.set("minPrice", uiState.minPrice.value.toDoubleOrNull())
                                navController.currentBackStackEntry?.savedStateHandle?.set("maxPrice", uiState.maxPrice.value.toDoubleOrNull())
                                navController.currentBackStackEntry?.savedStateHandle?.set("maxSurface", uiState.maxSurface.value.toDoubleOrNull())
                                navController.currentBackStackEntry?.savedStateHandle?.set("minSurface", uiState.minSurface.value.toDoubleOrNull())
                                navController.currentBackStackEntry?.savedStateHandle?.set("maxNbRooms", uiState.maxNbRooms.value.toIntOrNull())
                                navController.currentBackStackEntry?.savedStateHandle?.set("minNbRooms", uiState.minNbRooms.value.toIntOrNull())
                                navController.currentBackStackEntry?.savedStateHandle?.set("isAvailable", uiState.isAvailable)
                                navController.currentBackStackEntry?.savedStateHandle?.set("commodities", uiState.commoditiesForQuery)
                                navController.currentBackStackEntry?.savedStateHandle?.set("dateSale", uiState.dateSale.value)
                                viewModel.searchProperties()
                                navController.navigate("all_properties")

                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Search")
                        }

                        if (uiState.listProperties.isNotEmpty()){
                            Column {
                                for (property in uiState.listProperties){
                                    Text("Property ${property.id} : ${property.name}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }


}