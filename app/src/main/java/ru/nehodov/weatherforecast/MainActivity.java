package ru.nehodov.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_fragment);
        Toolbar toolbar = findViewById(R.id.toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_white_24);
//        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Погода в Москве сейчас");
    }
}