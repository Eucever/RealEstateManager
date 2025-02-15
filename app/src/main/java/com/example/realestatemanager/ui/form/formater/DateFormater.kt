package com.example.realestatemanager.ui.form.formater

import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

class DateFormater {
    fun format(instant : Instant, pattern : String ):String{
        val zone = ZoneId.systemDefault()
        val localDateTime = instant.atZone(zone).toLocalDateTime()

        val formatter = DateTimeFormatter.ofPattern(pattern)
        val formattedDate = localDateTime.format(formatter)
        return formattedDate
    }
}