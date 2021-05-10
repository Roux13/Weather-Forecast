package ru.nehodov.weatherforecast.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import ru.nehodov.weatherforecast.R

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val toolbar = findViewById<Toolbar>(R.id.settingsToolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }
}