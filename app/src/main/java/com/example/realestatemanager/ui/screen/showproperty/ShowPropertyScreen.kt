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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.realestatemanager.domain.model.Agent
import com.example.realestatemanager.domain.model.Location
import com.example.realestatemanager.domain.model.Property
import com.example.realestatemanager.ui.component.ImageSection
import com.example.realestatemanager.ui.component.RoundedTextField
import com.example.realestatemanager.ui.form.formater.DateFormater
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

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


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PropertyDetail(property : Property,
                   onFormatDate: (Instant, String) -> String
){
    ImageSection(property.pictures)

    Spacer(modifier = Modifier.height(5.dp))

    Column (modifier = Modifier
        .fillMaxWidth()
        .border(BorderStroke( width = 2.dp, color = MaterialTheme.colorScheme.tertiary))
    ){
        Column(modifier = Modifier.padding(5.dp)
            .padding(start = 10.dp)) {
            Text(
                text = "Details",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge,
                textDecoration = TextDecoration.Underline
            )
        }
        Column {
            Column(modifier = Modifier.padding(10.dp)
                .padding(start = 12.dp)) {
                Text(
                    text = "Name",
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleMedium
                )
                RoundedTextField(property.name)
            }

            if (property.description!= null){
                Column(modifier = Modifier.padding(10.dp)
                    .padding(start = 12.dp)) {
                    Text(
                        text = "Description",
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.titleMedium
                    )
                    RoundedTextField(property.description)
                }
            }


            if (property.type?.name != null){
                Column(modifier = Modifier.padding(10.dp)
                    .padding(start = 12.dp)) {
                    Text(
                        text = "Type",
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.titleMedium
                    )
                    RoundedTextField(property.type.name)
                }
            }

            if (property.surface != null){
                Column(modifier = Modifier.padding(10.dp)
                    .padding(start = 12.dp)) {
                    Text(
                        text = "Surface",
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.titleMedium
                    )
                    RoundedTextField(property.surface.toString() + "m")
                }
            }

            if (property.nbRooms != null){
                Column(modifier = Modifier.padding(10.dp)
                    .padding(start = 12.dp)) {
                    Text(
                        text = "Number of Rooms",
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.titleMedium
                    )
                    RoundedTextField(property.nbRooms.toString())
                }
            }

            if (property.nbBedrooms != null){
                Column(modifier = Modifier.padding(10.dp)
                    .padding(start = 12.dp)) {
                    Text(
                        text = "Number of Bedrooms",
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.titleMedium
                    )
                    RoundedTextField(property.nbBedrooms.toString())
                }
            }

            if (property.nbBathrooms != null){
                Column(modifier = Modifier.padding(10.dp)
                    .padding(start = 12.dp)) {
                    Text(
                        text = "Number of Bathrooms",
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.titleMedium
                    )
                    RoundedTextField(property.nbBathrooms.toString())
                }
            }

            if (property.price != null){
                Column(modifier = Modifier.padding(10.dp)
                    .padding(start = 12.dp)) {
                    Text(
                        text = "Price",
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.titleMedium
                    )
                    RoundedTextField(property.price.toString() + "$")
                }
            }


            Column(modifier = Modifier.padding(10.dp)
                .padding(start = 12.dp)) {
                Text(
                    text = "Date of Creation",
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleMedium
                )

                RoundedTextField(onFormatDate (property.dateCreation, "dd-MM-yyyy"))
            }

            if (property.dateEntry != null){
                Column(modifier = Modifier.padding(10.dp)
                    .padding(start = 12.dp)) {
                    Text(
                        text = "Date of Entry",
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.titleMedium
                    )

                    RoundedTextField(
                        onFormatDate (
                            property.dateEntry,
                            "dd-MM-yyyy"
                        )
                    )
                }
            }

            if (property.isSold) {
                Column(modifier = Modifier.padding(10.dp)
                    .padding(start = 12.dp)) {
                    Text(
                        text = "Date of Sale",
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.titleMedium
                    )

                    RoundedTextField(onFormatDate (property.dateSale!!, "dd-MM-yyyy"))
                }
            }

            if (property.commodities.isNotEmpty()){
                Column(modifier = Modifier.padding(10.dp)
                    .padding(start = 12.dp)) {
                    Text(
                        text = "Commodities",
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.titleMedium
                    )
                    FlowRow{
                        for (commodity in property.commodities){
                            RoundedTextField(commodity.type, 5)
                        }
                    }
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(5.dp))

    Column (modifier = Modifier
        .fillMaxWidth()
        .border(BorderStroke( width = 2.dp, color = MaterialTheme.colorScheme.tertiary))
    ){
        if (property.location != null){
            LocationSection(property.location, property.apartmentNumber ?: -1)
        }
    }

    Spacer(modifier = Modifier.height(5.dp))

    Column (modifier = Modifier
        .fillMaxWidth()
        .border(BorderStroke( width = 2.dp, color = MaterialTheme.colorScheme.tertiary))
    ) {
        if (property.agent != null){
            AgentSection(property.agent)
        }
    }
}


@Composable
fun LocationSection(location: Location, apartmentNb : Int){
    Spacer(modifier = Modifier.height(4.dp))

    Column (modifier = Modifier.padding(5.dp)
        .padding(start = 10.dp)){
        Text(
            text = "Location",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge,
            textDecoration = TextDecoration.Underline
        )
    }

    Column(modifier = Modifier.padding(10.dp)
        .padding(start = 12.dp)) {
        Text(
            text = "Street",
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.titleMedium
        )
        RoundedTextField(location.street)
    }

    if (location.streetNumber != null){
        Column(modifier = Modifier.padding(10.dp)
            .padding(start = 12.dp)) {
            Text(
                text = "Street Number",
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleMedium
            )
            RoundedTextField(location.streetNumber.toString())
        }
    }

    if(apartmentNb != -1){
        Column(modifier = Modifier.padding(10.dp)
            .padding(start = 12.dp)) {
            Text(
                text = "Apartment Number",
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleMedium
            )
            RoundedTextField(apartmentNb.toString())
        }
    }

    Column(modifier = Modifier.padding(10.dp)
        .padding(start = 12.dp)) {
        Text(
            text = "Postal code",
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.titleMedium
        )
        RoundedTextField(location.postalCode)
    }

    Column(modifier = Modifier.padding(10.dp)
        .padding(start = 12.dp)) {
        Text(
            text = "City",
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.titleMedium
        )
        RoundedTextField(location.city)
    }

    Column(modifier = Modifier.padding(10.dp)
        .padding(start = 12.dp)) {
        Text(
            text = "Country",
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.titleMedium
        )
        RoundedTextField(location.country)
    }

    if (location.longitude!= null && location.latitude != null){
        Row(modifier = Modifier.fillMaxWidth()){
            Column(modifier = Modifier.padding(10.dp)
                .padding(start = 12.dp)) {
                Text(
                    text = "Lattitude",
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleMedium
                )
                RoundedTextField(location.latitude.toString())
            }

            Column(modifier = Modifier.padding(10.dp)
                .padding(start = 12.dp)) {
                Text(
                    text = "Longitude",
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleMedium
                )
                RoundedTextField(location.longitude.toString())
            }
        }
    }
}

@Composable
fun AgentSection(agent: Agent){
    Spacer(modifier = Modifier.height(4.dp))

    Column (modifier = Modifier.padding(5.dp)
        .padding(start = 10.dp)){
        Text(
            text = "Agent",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge,
            textDecoration = TextDecoration.Underline
        )
    }

    Column(modifier = Modifier.padding(10.dp)
        .padding(start = 12.dp)) {
        Text(
            text = "First Name",
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.titleMedium
        )
        RoundedTextField(agent.firstName)
    }

    Column(modifier = Modifier.padding(10.dp)
        .padding(start = 12.dp)) {
        Text(
            text = "Last Name",
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.titleMedium
        )
        RoundedTextField(agent.lastName)
    }

    Column(modifier = Modifier.padding(10.dp)
        .padding(start = 12.dp)) {
        Text(
            text = "Email",
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.titleMedium
        )
        RoundedTextField(agent.email)
    }

    Column(modifier = Modifier.padding(10.dp)
        .padding(start = 12.dp)) {
        Text(
            text = "Phone Number",
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.titleMedium
        )
        RoundedTextField(agent.phone)
    }
}