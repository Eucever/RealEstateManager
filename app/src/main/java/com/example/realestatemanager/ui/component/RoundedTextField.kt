package com.example.realestatemanager.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RoundedTextField(text : String){
    Spacer(modifier = Modifier.height(3.dp))

    Surface(shape = MaterialTheme.shapes.medium, shadowElevation = 1.dp) {
        Text(
            text = text,
            modifier = Modifier.padding(all = 4.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun RoundedTextField(text : String, endDp : Int){
    Spacer(modifier = Modifier.height(3.dp))

    Surface(shape = MaterialTheme.shapes.medium, shadowElevation = 1.dp, modifier = Modifier.padding(end = endDp.dp, bottom = 1.dp)) {
        Text(
            text = text,
            modifier = Modifier.padding(all = 4.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}