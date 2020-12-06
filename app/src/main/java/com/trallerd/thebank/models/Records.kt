package com.trallerd.thebank.models

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

class Records(
    var value: Float,
    var person: String,
    var remarks: String,
    var receive: Boolean = false,
    @SerializedName("fk_user") var fkUser: Long?,
    @SerializedName("regitred_at") var registredAt: String = SimpleDateFormat("dd/MM/yyyy HH:mm").format(Date())
) {
    var id: Long? = null

    override fun equals(other: Any?) = other is Records && this.id == other.id;
}