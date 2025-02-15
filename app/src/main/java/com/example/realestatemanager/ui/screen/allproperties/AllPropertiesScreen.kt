package com.example.realestatemanager.ui.screen.allproperties


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.realestatemanager.domain.model.Property
import org.threeten.bp.Instant

@Composable
fun AllPropertiesScreen(navController: NavController, viewModel: AllPropertiesViewModel = hiltViewModel()){
    var selectedTab by remember { mutableStateOf(0) }

    val uiState by viewModel.uiState.collectAsState()


    val argumentEstateType = navController.previousBackStackEntry?.savedStateHandle?.get<Long?>("type")
    val argumentMinPrice = navController.previousBackStackEntry?.savedStateHandle?.get<Double?>("minPrice")
    val argumentMaxPrice = navController.previousBackStackEntry?.savedStateHandle?.get<Double?>("maxPrice")
    val argumentMinSurface = navController.previousBackStackEntry?.savedStateHandle?.get<Double?>("minSurface")
    val argumentMaxSurface = navController.previousBackStackEntry?.savedStateHandle?.get<Double?>("maxSurface")
    val argumentMinNbRooms = navController.previousBackStackEntry?.savedStateHandle?.get<Int?>("minNbRooms")
    val argumentMaxNbRooms = navController.previousBackStackEntry?.savedStateHandle?.get<Int?>("maxNbRooms")
    val argumentIsAvailable = navController.previousBackStackEntry?.savedStateHandle?.get<Boolean?>("isAvailable")
    val argumentCommodities = navController.previousBackStackEntry?.savedStateHandle?.get<List<Long>?>("commodities")
    val argumentDateSale = navController.previousBackStackEntry?.savedStateHandle?.get<Instant?>("dateSale")



    LaunchedEffect(Unit) {
        if (argumentEstateType != null
            ||argumentMinPrice != null
            || argumentMaxPrice != null
            || argumentMinNbRooms != null
            || argumentMaxNbRooms != null
            || argumentIsAvailable != null
            || argumentCommodities != null
            || argumentDateSale != null){
            viewModel.searchPropertyWithArgs(argumentEstateType,
                argumentMinPrice,
                argumentMaxPrice,
                argumentMinSurface,
                argumentMaxSurface,
                argumentMinNbRooms,
                argumentMaxNbRooms,
                argumentIsAvailable,
                argumentCommodities,
                argumentDateSale)
        }
    }

    Column(modifier = Modifier.fillMaxWidth()){
        TabRow(selectedTabIndex = selectedTab) {
            Tab(selected = selectedTab==0, onClick = {selectedTab = 0} ){
                Text("List View")
            }
            Tab(selected = selectedTab==1, onClick = {selectedTab = 1}){
                Text("Map View")
            }
        }

        when(selectedTab){
            0 -> {
                when(uiState){
                    is AllPropertiesUIState.IsLoading ->{
                        Text("Loading... Please Wait")
                    }
                    is AllPropertiesUIState.Success ->{
                        if (argumentEstateType != null
                            ||argumentMinPrice != null
                            || argumentMaxPrice != null
                            || argumentMinNbRooms != null
                            || argumentMaxNbRooms != null
                            || argumentIsAvailable != null
                            || argumentCommodities != null){
                            Column {
                                ResetFilterButton(onFilterResetClick = {
                                    navController.previousBackStackEntry?.savedStateHandle?.remove<Long?>("type")
                                    navController.previousBackStackEntry?.savedStateHandle?.remove<Double?>("minPrice")
                                    navController.previousBackStackEntry?.savedStateHandle?.remove<Double?>("maxPrice")
                                    navController.previousBackStackEntry?.savedStateHandle?.remove<Double?>("maxSurface")
                                    navController.previousBackStackEntry?.savedStateHandle?.remove<Double?>("minSurface")
                                    navController.previousBackStackEntry?.savedStateHandle?.remove<Int?>("maxNbRooms")
                                    navController.previousBackStackEntry?.savedStateHandle?.remove<Int?>("minNbRooms")
                                    navController.previousBackStackEntry?.savedStateHandle?.remove<Boolean?>("isAvailable")
                                    navController.previousBackStackEntry?.savedStateHandle?.remove<List<Long>?>("commodities")
                                    navController.previousBackStackEntry?.savedStateHandle?.remove<Instant?>("dateSale")
                                    viewModel.resetFilters()
                                })
                            }
                        }
                        PropertyListView(properties = (uiState as AllPropertiesUIState.Success).listProperties, onPropertyEditClick = { propertyId ->
                            navController.navigate("edit_property/${propertyId}")
                        },onPropertyShowClick = { propertyId ->
                            navController.navigate("show_property/${propertyId}")
                        }, onPropertyDeleteClick = {
                            property -> viewModel.deleteProperty(property)
                        })
                    }
                    is AllPropertiesUIState.Error -> {
                        Text("Error: ${(uiState as AllPropertiesUIState.Error).sError}")
                    }
                    else -> {
                        Text("Error: Unknown state")
                    }

                }
            }
            1 -> PropertyMapView()
        }

    }
}

@Composable
fun PropertyListView(properties: List<Property>,
                     onPropertyEditClick: (Long) -> Unit,
                     onPropertyShowClick: (Long) -> Unit,
                     onPropertyDeleteClick: (Property) -> Unit)
    {
    LazyColumn {
        items(properties){ property ->
            PropertyListItem(property = property,
                onPropertyEditClick = onPropertyEditClick,
                onPropertyShowClick = onPropertyShowClick,
                onPropertyDeleteClick = onPropertyDeleteClick
            )
        }
    }
}

@Composable
fun PropertyListItem(property: Property,
                     onPropertyEditClick: (Long) -> Unit,
                     onPropertyShowClick: (Long) -> Unit,
                     onPropertyDeleteClick : (Property) -> Unit
    ){
    Row(
        modifier = Modifier
            .wrapContentSize()
            .padding(8.dp)
            .border(BorderStroke( width = 1.dp, color = MaterialTheme.colorScheme.tertiary))
    ){
        Column (
            modifier = Modifier
                .weight(0.60f)
                .padding(6.dp)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            if(property.pictures.isNotEmpty()){
                Image(
                    bitmap = property.pictures[0].content.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxSize()
                    .padding(5.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Box(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.surfaceContainer),

                ) {
                Column(modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize()
                ) {
                    property.type?.name?.let { Text(text = it,
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.titleMedium) }
                    property.location?.street?.let { Text(text = it,
                        color = MaterialTheme.colorScheme.tertiary,
                        style = MaterialTheme.typography.titleSmall) }
                    property.price?.let { Text(text = "$$it",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleLarge) }
                    if (property.isSold){
                        Text(text = "Sold",
                            color = Color(0xFFA10000),
                            style = MaterialTheme.typography.displaySmall
                        )
                    }else{
                        Text(text = "Available",
                            color = Color(0xFF1DAF03),
                            style = MaterialTheme.typography.displaySmall,
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .weight(0.30f)
                .padding(6.dp)
                .fillMaxHeight()
                .align(Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onPropertyShowClick(property.id!!)
                }
            ) {
                Text(text = "Show")
            }
            Spacer(modifier = Modifier.height(5.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onPropertyEditClick(property.id!!)
                }
            ) {
                Text(text = "Edit")
            }
            Spacer(modifier = Modifier.height(5.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onPropertyDeleteClick(property)
                }
            ) {
                Text(text = "Delete")
            }

        }
    }
}

@Composable
fun ResetFilterButton(onFilterResetClick : () -> Unit){
    Button(modifier = Modifier.fillMaxWidth()
        .padding(5.dp),
        onClick = {
            onFilterResetClick()
        })
    {
        Text(text = "Reset Filters")
    }
}

@Composable
fun PropertyMapView(){
    Text("Map View - To be implemented with Google Maps")

}