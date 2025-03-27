package com.example.realestatemanager.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.example.realestatemanager.domain.model.Location

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