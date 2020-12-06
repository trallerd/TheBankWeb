package com.trallerd.thebank.models

class Users(
    var username: String,
    var password: String
) {
    var id: Long? = null

    override fun equals(other: Any?) = other is Users && this.id == other.id;
}