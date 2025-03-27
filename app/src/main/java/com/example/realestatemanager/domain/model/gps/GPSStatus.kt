package com.example.realestatemanager.domain.model.gps

data class GPSStatus(
     var longitude: Double? = null,

     var latitude: Double? = null,

     var hasGpsPermission: Boolean? = null,

     var querying: Boolean? = null
) {

    override fun toString(): String {
        return "GPSStatus{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                ", hasGpsPermission=" + hasGpsPermission +
                ", querying=" + querying +
                '}'
    }
}