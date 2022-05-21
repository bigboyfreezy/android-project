package com.farah.ecom.model

import com.google.gson.annotations.SerializedName

data class proinfo (
    @SerializedName("order_code") val order_code : String?
)