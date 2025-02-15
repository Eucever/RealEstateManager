package com.example.realestatemanager.ui.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.realestatemanager.ui.navigation.PropertyNavHost


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(){
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Real Estate Manage")
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ){
        paddingValues ->
        PropertyNavHost(navController, modifier = Modifier.padding(paddingValues))
    }

}


@Composable
fun BottomNavigationBar(navController: NavController){
    val items = listOf(
        "All Properties" to Icons.Filled.Home,
        "Create Property" to Icons.Filled.AddCircle,
        "Search Properties" to Icons.Filled.Search,
        "Loan Calculator" to Icons.Filled.Info
    )

    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp > 600

    BottomNavigation{
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ){
            items.forEachIndexed{index, (label, icon) ->
                BottomNavigationItem(
                    selected = false,
                    onClick = {
                        navController.navigate(label.replace(" ", "_").lowercase())
                    },
                    label = {
                        val shortLabel = when(label){
                            "All Properties" -> "All"
                            "Create Property" -> "Create"
                            "Search Properties" -> "Search"
                            "Calculate Loan" -> "Loan"
                            else -> label
                        }
                        val displayLabel = if (isTablet) label else shortLabel
                        Text(displayLabel)
                    },
                    icon = {
                        Icon(icon, contentDescription = label )
                    }
                )
            }
        }
    }
}

@Composable
fun BottomNavigation(content: @Composable () -> Unit) {
    content()
}

@Composable
fun BottomNavigationItem(selected: Boolean, onClick: () -> Unit, label: @Composable () -> Unit, icon: @Composable () -> Unit) {
    TextButton(onClick = onClick) {
        icon()
        label()
    }
}