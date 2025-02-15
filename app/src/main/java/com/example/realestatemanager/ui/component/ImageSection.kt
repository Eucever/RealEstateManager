package com.example.realestatemanager.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.realestatemanager.domain.model.Picture

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ImageSection(pictures : List<Picture>){
    Column (modifier = Modifier
        .fillMaxWidth()
        .border(BorderStroke( width = 2.dp, color = MaterialTheme.colorScheme.tertiary))
    ) {
        FlowRow {
            for (picture in pictures){
                Image(bitmap = picture.content.asImageBitmap(), contentDescription = null, modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxSize()
                    .padding(5.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}