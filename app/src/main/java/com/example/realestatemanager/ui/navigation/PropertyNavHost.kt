package com.example.realestatemanager.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.realestatemanager.ui.screen.allproperties.AllPropertiesScreen
import com.example.realestatemanager.ui.screen.createproperty.CreatePropertyScreen
import com.example.realestatemanager.ui.screen.loancalculator.LoanCalculatorScreen
import com.example.realestatemanager.ui.screen.searchproperties.SearchPropertiesScreen
import com.example.realestatemanager.ui.screen.showproperty.ShowPropertyScreen

@Composable
fun PropertyNavHost(navController: NavHostController, modifier: Modifier) {
    NavHost(navController, startDestination = "all_properties", modifier = modifier) {
        composable("all_properties") {
            AllPropertiesScreen( navController)
        }
        composable("show_property/{propertyId}") { backStackEntry ->
            val propertyId = backStackEntry.arguments?.getString("propertyId")?.toLong()
            if (propertyId != null){
                ShowPropertyScreen(propertyId)
            }
        }
        composable("create_property") { CreatePropertyScreen(null) }
        composable("search_properties") { SearchPropertiesScreen(navController) }
        composable("loan_calculator") { LoanCalculatorScreen() }
        composable("edit_property/{propertyId}"){ backStackEntry ->
            val propertyId = backStackEntry.arguments?.getString("propertyId")?.toLong()
            if (propertyId != null){
                CreatePropertyScreen(propertyId)
            }
        }

    }
}