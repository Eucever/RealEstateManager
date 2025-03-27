package com.example.realestatemanager.utils

import android.content.Context
import android.util.Log

object ResponsiveUtils {
    fun isTablet(context: Context): Boolean{
        return context.resources.configuration.smallestScreenWidthDp >= 600
    }
}