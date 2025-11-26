package com.example.remote

import android.content.Context

class TemperatureRepository(context: Context) {

    private val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun getTemperature(): Int {
        // Devuelve la temperatura guardada, o 17 como valor predeterminado si no hay ninguna.
        return sharedPreferences.getInt(KEY_TEMPERATURE, 17)
    }

    fun saveTemperature(temperature: Int) {
        sharedPreferences.edit().putInt(KEY_TEMPERATURE, temperature).apply()
    }

    companion object {
        private const val KEY_TEMPERATURE = "temperature"
    }
}
