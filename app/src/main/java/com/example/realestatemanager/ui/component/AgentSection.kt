package com.example.realestatemanager.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.realestatemanager.domain.model.Agent

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