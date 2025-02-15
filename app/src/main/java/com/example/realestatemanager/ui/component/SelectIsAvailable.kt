package com.example.realestatemanager.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SelectIsAvailable(
    isAvailableSelected : Boolean,
    onIsAvailableSelected : (Boolean) -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isAvailableSelected,
            onCheckedChange = {
                val newIsAvailableSelected = !isAvailableSelected
                onIsAvailableSelected(newIsAvailableSelected)
            }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text("Is Available ?")
    }
}