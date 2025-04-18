package com.example.realestatemanager.ui.screen.createproperty

import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.realestatemanager.ui.component.ImageSelector
import com.example.realestatemanager.ui.component.InstantDateSelectionField
import com.example.realestatemanager.ui.component.SelectAgentField
import com.example.realestatemanager.ui.component.SelectCommoditiesField
import com.example.realestatemanager.ui.component.SelectEstateTypeField
import com.example.realestatemanager.ui.form.state.LocationFormState

const val CPS_TAG = "CreatePropertyScreen"
const val CPS_MAX_PICTURES_TO_PICK = 10
const val CPS_MAX_DESCRIPTION_LINES = 5

@Composable
fun CreatePropertyScreen(
    propertyId : Long?,
    viewModel: CreatePropertyViewModel = hiltViewModel()
){
    val context =  LocalContext.current
    val uiState = viewModel.uiState.collectAsState().value

    val pickMultipleMedia = rememberLauncherForActivityResult(
        ActivityResultContracts.PickMultipleVisualMedia(CPS_MAX_PICTURES_TO_PICK)
    ) { uris ->
        if(uris.isNotEmpty()){
            Log.d(CPS_TAG, "Number of items selected : ${uris.size}")
            val bitmaps = uris.mapNotNull { uri ->
                try{
                    val bitmap = if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.P){
                        ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, uri))
                    } else{
                        MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                    }
                    bitmap
                } catch (e: Exception){
                    Log.e(CPS_TAG, "Failed to load image : ${e.localizedMessage}")
                    null
                }
            }
            viewModel.addPictures(bitmaps)
        }
    }

    LaunchedEffect(uiState) {
        Log.d(CPS_TAG, "UIState changed : $uiState")

        viewModel.loadForEditing(propertyId)
    }

    LazyColumn (modifier = Modifier.padding(16.dp)){
        when(uiState){
            is CreatePropertyUIState.IsLoading -> {
                item {
                    Text("Loading...Please Wait")
                }

            }
            is CreatePropertyUIState.Success -> {
                item {
                Text(if(propertyId == null)"Property Created Sucessfully with ID: ${uiState.propertyId}" else
                    "Property Updated Sucessfully with ID: ${uiState.propertyId}")
                }
            }
            is CreatePropertyUIState.Error -> {
                item {
                    Text("Error: ${uiState.sError ?: "Unknown Error"}")
                }
            }
            is CreatePropertyUIState.FormState -> {
                item {
                    Text( if (propertyId == null )"Create Property" else "Edit Property")

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = uiState.name.value,
                        onValueChange = {viewModel.updateFieldValue("name", it)},
                        label = { Text("Property Name *")},
                        modifier = Modifier.fillMaxWidth(),
                        isError = !uiState.name.isValid,
                        supportingText = {uiState.name.errorMessage?.let { Text(it)}}
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = uiState.description.value,
                        onValueChange = { viewModel.updateFieldValue("description", it) },
                        label = { Text("Description") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = !uiState.description.isValid,
                        supportingText = { uiState.description.errorMessage?.let { Text(it) } },
                        maxLines = CPS_MAX_DESCRIPTION_LINES,
                        minLines = CPS_MAX_DESCRIPTION_LINES,
                        singleLine = false
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = uiState.surface.value,
                        onValueChange = { viewModel.updateFieldValue("surface", it) },
                        label = { Text("Surface Area") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = !uiState.surface.isValid,
                        supportingText = { uiState.surface.errorMessage?.let { Text(it) } }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = uiState.nbRooms.value,
                        onValueChange = { viewModel.updateFieldValue("nbRooms", it) },
                        label = { Text("Number of Rooms") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = !uiState.nbRooms.isValid,
                        supportingText = { uiState.nbRooms.errorMessage?.let { Text(it) } }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = uiState.nbBathrooms.value,
                        onValueChange = { viewModel.updateFieldValue("nbBathrooms", it) },
                        label = { Text("Number of Bathrooms") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = !uiState.nbBathrooms.isValid,
                        supportingText = { uiState.nbBathrooms.errorMessage?.let { Text(it) } }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = uiState.nbBedrooms.value,
                        onValueChange = { viewModel.updateFieldValue("nbBedrooms", it) },
                        label = { Text("Number of Bedrooms") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = !uiState.nbBedrooms.isValid,
                        supportingText = { uiState.nbBedrooms.errorMessage?.let { Text(it) } }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = uiState.price.value,
                        onValueChange = { viewModel.updateFieldValue("price", it) },
                        label = { Text("Price") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = !uiState.price.isValid,
                        supportingText = { uiState.price.errorMessage?.let { Text(it) } }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    InstantDateSelectionField(
                        label = "Entry Date",
                        selectedInstant = uiState.dateEntry.value,
                        onDateSelected = { newInstant ->
                            viewModel.updateFieldValue("dateEntry", newInstant)
                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    InstantDateSelectionField(
                        label = "Sale Date",
                        selectedInstant = uiState.dateSale.value,
                        onDateSelected = { newInstant ->
                            viewModel.updateFieldValue("dateSale", newInstant)
                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = uiState.apartmentNumber.value,
                        onValueChange = { viewModel.updateFieldValue("apartmentNumber", it) },
                        label = { Text("Apartment Number") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = !uiState.apartmentNumber.isValid,
                        supportingText = { uiState.apartmentNumber.errorMessage?.let { Text(it) } }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    LocationFields(uiState.location) { field, value ->
                        viewModel.updateFieldValue(field,value)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    SelectAgentField(uiState.allAgents, uiState.selectedAgent){ agent ->
                        viewModel.updateSelectedAgent(agent)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    SelectEstateTypeField(uiState.allEstateTypes, uiState.selectedEstateType){
                        viewModel.updateSelectedEstateType(it)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    SelectCommoditiesField(uiState.allCommodities, uiState.selectedCommodities){
                        viewModel.updateSelectedCommodities(it)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { pickMultipleMedia.launch(PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly)) },
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text("Select Pictures")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    ImageSelector(
                        uiState = uiState,
                        onDelete = { picture -> viewModel.deletePicture(picture) },
                        onMoveUp = { picture -> viewModel.movePictureUp(picture) },
                        onMoveDown = { picture -> viewModel.movePictureDown(picture) }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { viewModel.saveProperty() },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = uiState.isFormValid
                    ) {
                        Text("Save Property")
                    }
                }
            }
        }
    }
}

@Composable
fun LocationFields(
    location: LocationFormState,
    onLocationChange: (String, String) -> Unit
) {
    Column {
        OutlinedTextField(
            value = location.streetNumber.value,
            onValueChange = { onLocationChange("location.streetNumber", it) },
            label = { Text("Street number") },
            modifier = Modifier.fillMaxWidth(),
            isError = !location.streetNumber.isValid,
            supportingText = { location.streetNumber.errorMessage?.let { Text(it) } }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = location.street.value,
            onValueChange = { onLocationChange("location.street", it) },
            label = { Text("Street *") },
            modifier = Modifier.fillMaxWidth(),
            isError = !location.street.isValid,
            supportingText = { location.street.errorMessage?.let { Text(it) } }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = location.postalCode.value,
            onValueChange = { onLocationChange("location.postalCode", it) },
            label = { Text("Postal Code *") },
            modifier = Modifier.fillMaxWidth(),
            isError = !location.postalCode.isValid,
            supportingText = { location.postalCode.errorMessage?.let { Text(it) } }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = location.city.value,
            onValueChange = { onLocationChange("location.city", it) },
            label = { Text("City *") },
            modifier = Modifier.fillMaxWidth(),
            isError = !location.city.isValid,
            supportingText = { location.city.errorMessage?.let { Text(it) } }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = location.country.value,
            onValueChange = { onLocationChange("location.country", it) },
            label = { Text("Country *") },
            modifier = Modifier.fillMaxWidth(),
            isError = !location.country.isValid,
            supportingText = { location.country.errorMessage?.let { Text(it) } }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = location.longitude.value,
            onValueChange = { onLocationChange("location.longitude", it) },
            label = { Text("Longitude") },
            modifier = Modifier.fillMaxWidth(),
            isError = !location.longitude.isValid,
            supportingText = { location.longitude.errorMessage?.let { Text(it) } }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = location.latitude.value,
            onValueChange = { onLocationChange("location.latitude", it) },
            label = { Text("Latitude") },
            modifier = Modifier.fillMaxWidth(),
            isError = !location.latitude.isValid,
            supportingText = { location.latitude.errorMessage?.let { Text(it) } }
        )
    }
}