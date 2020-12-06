package com.trallerd.thebank

import android.app.Application
import com.trallerd.thebank.models.Users

class Controller : Application() {
    companion object {
        var users = Users("", "")
    }
}