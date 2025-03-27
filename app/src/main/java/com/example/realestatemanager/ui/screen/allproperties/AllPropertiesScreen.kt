package com.example.realestatemanager.ui.screen.allproperties


import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.example.realestatemanager.domain.model.Property
import com.example.realestatemanager.ui.component.PropertyDetail
import com.example.realestatemanager.utils.ResponsiveUtils
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.GoogleMapFactory
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import org.threeten.bp.Instant

@Composable
fun AllPropertiesScreen(navController: NavController, viewModel: AllPropertiesViewModel = hiltViewModel()){
    var selectedTab by remember { mutableStateOf(0) }

    val uiState by viewModel.uiState.collectAsState()

    val lifecycleOwner = LocalLifecycleOwner.current

    val cameraState = rememberCameraPositionState()

    val isTablet = ResponsiveUtils.isTablet(LocalContext.current)

    val context = LocalContext.current

    var position : LatLng? = null


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
            viewModel.startLocationUpdates()
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
                                    viewModel.startLocationUpdates()
                                })

                            }
                        }

                        if(isTablet){
                            if((uiState as AllPropertiesUIState.Success).currentProperty != null){
                                Row(
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .padding(8.dp)
                                ){
                                     PropertyListView(modifier = Modifier
                                         .weight(0.30f)
                                         .padding(6.dp)
                                         .fillMaxHeight(),
                                         properties = (uiState as AllPropertiesUIState.Success).listProperties, onPropertyEditClick = { propertyId ->
                                            navController.navigate("edit_property/${propertyId}")
                                        },onPropertyShowClick = { propertyId ->
                                            viewModel.updateCurrentProperty(propertyId)
                                        }, onPropertyDeleteClick = {
                                                property -> viewModel.deleteProperty(property)
                                        })


                                    Column (modifier = Modifier.weight(0.30f)
                                        .padding(6.dp)
                                        .fillMaxHeight()){
                                        IconButton(onClick = {
                                            viewModel.resetCurrentProperty()
                                        } ) {
                                            Icon(Icons.Filled.Close, contentDescription = "Close Details")
                                        }
                                        PropertyTabletDetail(modifier = Modifier,
                                            property = (uiState as AllPropertiesUIState.Success).currentProperty!!,
                                            onFormatDate = { i, s ->
                                                viewModel.format(i, s)
                                            })
                                    }

                                }
                            }else{
                                PropertyListView(properties = (uiState as AllPropertiesUIState.Success).listProperties, onPropertyEditClick = { propertyId ->
                                        navController.navigate("edit_property/${propertyId}")
                                    },onPropertyShowClick = { propertyId ->
                                        viewModel.updateCurrentProperty(propertyId)
                                    }, onPropertyDeleteClick = {
                                        property -> viewModel.deleteProperty(property)
                                    })
                                }
                        }else{
                            PropertyListView(properties = (uiState as AllPropertiesUIState.Success).listProperties, onPropertyEditClick = { propertyId ->
                                navController.navigate("edit_property/${propertyId}")
                            },onPropertyShowClick = { propertyId ->
                                navController.navigate("show_property/${propertyId}")
                            }, onPropertyDeleteClick = {
                                    property -> viewModel.deleteProperty(property)
                            })
                        }

                    }
                    is AllPropertiesUIState.Error -> {
                        Text("Error: ${(uiState as AllPropertiesUIState.Error).sError}")
                    }
                    else -> {
                        Text("Error: Unknown state")
                    }

                }
            }
            1 -> {
                when (uiState) {
                    is AllPropertiesUIState.IsLoading -> {
                        Text("Loading... Please Wait")
                    }

                    is AllPropertiesUIState.Success -> {
                        val propertyPosition = if((uiState as AllPropertiesUIState.Success).propertyLocation == null)LatLng(0.0,0.0)else
                            (uiState as AllPropertiesUIState.Success).propertyLocation!!
                            if (isTablet) {
                                if ((uiState as AllPropertiesUIState.Success).currentProperty != null) {
                                    Row(
                                        modifier = Modifier
                                            .wrapContentSize()
                                            .padding(8.dp)
                                    ) {
                                        Box(Modifier
                                            .weight(0.30f)
                                            .padding(6.dp)
                                            .fillMaxHeight()){
                                            PropertyMapView(
                                                modifier = Modifier,
                                                properties = (uiState as AllPropertiesUIState.Success).listProperties,
                                                propertyPosition = LatLng(
                                                    (uiState as AllPropertiesUIState.Success).currentProperty?.location?.latitude!!,
                                                    (uiState as AllPropertiesUIState.Success).currentProperty?.location?.longitude!!),
                                                markerOnClick = { propertyId ->
                                                    viewModel.updateCurrentProperty(propertyId)
                                                }
                                            )
                                        }

                                        Column (modifier = Modifier.weight(0.30f)
                                            .padding(6.dp)
                                            .fillMaxHeight()){
                                            IconButton(onClick = {
                                                viewModel.resetCurrentProperty()
                                            } ) {
                                                Icon(Icons.Filled.Close, contentDescription = "Close Details")
                                            }
                                            PropertyTabletDetail(modifier = Modifier,
                                                property = (uiState as AllPropertiesUIState.Success).currentProperty!!,
                                                onFormatDate = { i, s ->
                                                    viewModel.format(i, s)
                                                })
                                        }

                                    }
                                } else {
                                    Box{
                                        PropertyMapView(properties = (uiState as AllPropertiesUIState.Success).listProperties,
                                            propertyPosition = propertyPosition,
                                            markerOnClick = { propertyId ->
                                                viewModel.updateCurrentProperty(propertyId)
                                            }
                                        )
                                    }

                            }
                                Box{
                                    PropertyMapView(properties = (uiState as AllPropertiesUIState.Success).listProperties,
                                        propertyPosition = propertyPosition,
                                        markerOnClick = { propertyId ->
                                            navController.navigate("show_property/${propertyId}")
                                        }
                                    )

                                }

                        }
                    }
                    is AllPropertiesUIState.Error -> {
                        Text("Error: ${(uiState as AllPropertiesUIState.Error).sError}")
                    }
                    else -> {
                        Text("Error: Unknown state")
                    }
                }
            }
        }
    }
}
@Composable
fun ShowLocationPermissionRationale() {
    AlertDialog(
        onDismissRequest = {
            //Logic when dismiss happens
        },
        title = {
            Text("Permission Required")
        },
        text = {
            Text("You need to approve this permission in order to...")
        },
        confirmButton = {
            TextButton(onClick = {
                //Logic when user confirms to accept permissions
            }) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                //Logic when user denies to accept permissions
            }) {
                Text("Deny")
            }
        })
}


@Composable
fun PropertyListView(modifier: Modifier,
                     properties: List<Property>,
                     onPropertyEditClick: (Long) -> Unit,
                     onPropertyShowClick: (Long) -> Unit,
                     onPropertyDeleteClick: (Property) -> Unit)
{
    LazyColumn (modifier = modifier){
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
            .border(BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.tertiary))
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
                modifier = Modifier.fillMaxSize(),
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
fun PropertyTabletDetail(modifier: Modifier, property: Property,onFormatDate: (Instant, String) -> String){
    Log.d("ALLPROPERTYSCREEN", "Current property name : ${property.name}")
    LazyColumn (
        modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally){
        item {
            PropertyDetail(property = property, onFormatDate = onFormatDate)
        }
    }


}

@Composable
fun ResetFilterButton(onFilterResetClick : () -> Unit){
    Button(modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp),
        onClick = {
            onFilterResetClick()
        })
    {
        Text(text = "Reset Filters")
    }
}

@Composable
fun PropertyMapView(properties: List<Property>, propertyPosition: LatLng, markerOnClick: (Long)-> Unit){
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(propertyPosition, 15f)
    }

    val uiSettings by remember {
        mutableStateOf(MapUiSettings(zoomControlsEnabled = true, myLocationButtonEnabled = true))
    }
    val mapProperties by remember {
        mutableStateOf(MapProperties(mapType = MapType.TERRAIN, isMyLocationEnabled = true))
    }

    GoogleMap(
        cameraPositionState = cameraPositionState,
        properties = mapProperties,
        uiSettings = uiSettings
    ) {
        for (property in properties){
            if ((property.location?.latitude != null && property.location.longitude != null) ){

                MarkerInfoWindow(
                    state = MarkerState(position = LatLng(property.location.latitude, property.location.longitude)),
                    title = property.name,
                    icon = BitmapDescriptorFactory.defaultMarker(if(property.isSold)BitmapDescriptorFactory.HUE_RED else BitmapDescriptorFactory.HUE_GREEN ),
                    onClick = {
                        markerOnClick(property.id!!)
                        cameraPositionState.move(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(LatLng(property.location.latitude, property.location.longitude), 20f)))
                    true
                    }
                ){
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .border(
                                    BorderStroke(1.dp, Color.Black),
                                    RoundedCornerShape(10)
                                )
                                .clip(RoundedCornerShape(10))
                                .background(Color.White)
                                .padding(20.dp)
                        ) {
                            Text(property.name, fontWeight = FontWeight.Bold, color = Color.Black)
                            if (property.description != null){
                                Text(property.description, fontWeight = FontWeight.Medium, color = Color.Black)
                            }
                    }
                }

            }
        }
    }
}

@Composable
fun PropertyMapView(modifier: Modifier, properties: List<Property>, propertyPosition: LatLng, markerOnClick: (Long)-> Unit) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(propertyPosition, 15f)
    }

    var uiSettings by remember {
        mutableStateOf(MapUiSettings(zoomControlsEnabled = true, myLocationButtonEnabled = true))
    }
    var mapProperties by remember {
        mutableStateOf(MapProperties(mapType = MapType.TERRAIN, isMyLocationEnabled = true))
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        properties = mapProperties,
        uiSettings = uiSettings
    ) {
        for (property in properties) {
            if ((property.location?.latitude != null && property.location.longitude != null)) {

                MarkerInfoWindow(
                    state = MarkerState(
                        position = LatLng(
                            property.location.latitude,
                            property.location.longitude
                        )
                    ),
                    title = property.name,
                    icon = BitmapDescriptorFactory.defaultMarker(if (property.isSold) BitmapDescriptorFactory.HUE_RED else BitmapDescriptorFactory.HUE_GREEN),
                    onClick = {
                        markerOnClick(property.id!!)
                        cameraPositionState.move(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(LatLng(property.location.latitude, property.location.longitude), 30f)))
                        true
                    }
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .border(
                                BorderStroke(1.dp, Color.Black),
                                RoundedCornerShape(10)
                            )
                            .clip(RoundedCornerShape(10))
                            .background(Color.White)
                            .padding(20.dp)
                    ) {
                        Text(property.name, fontWeight = FontWeight.Bold, color = Color.Black)
                        if (property.description != null) {
                            Text(
                                property.description,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )
                        }
                    }
                }

            }
        }
    }
}

private suspend fun CameraPositionState.centerOnLocation(
    location: LatLng
) = animate(
    update = CameraUpdateFactory.newLatLngZoom(
        location,
        15f
    ),
    durationMs = 1500
)