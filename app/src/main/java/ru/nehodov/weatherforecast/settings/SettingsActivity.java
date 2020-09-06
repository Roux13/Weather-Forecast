package ru.nehodov.weatherforecast.settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import ru.nehodov.weatherforecast.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.settingsToolbar);
        toolbar.setNavigationOnClickListener((view) -> onBackPressed());
    }
}