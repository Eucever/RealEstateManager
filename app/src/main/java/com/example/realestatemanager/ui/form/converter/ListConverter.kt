package com.example.realestatemanager.ui.form.converter

import android.util.Log
import com.example.realestatemanager.domain.model.Commodity

class ListConverter {
    fun toCommoditiesToIdList(commodities : List<Commodity>): List<Long>{
        val idsList: MutableList<Long> = mutableListOf()
        for (commodity in commodities){
            idsList.add(commodity.id!!)
        }
        return idsList
    }
}