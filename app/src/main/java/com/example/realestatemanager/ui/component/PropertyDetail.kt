package com.example.realestatemanager.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.realestatemanager.domain.model.Property
import org.threeten.bp.Instant

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