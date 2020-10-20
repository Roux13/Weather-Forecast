package ru.nehodov.weatherforecast.settings;

import android.os.Bundle;
import android.text.InputType;

import androidx.core.content.ContextCompat;
import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceFragmentCompat;

import ru.nehodov.weatherforecast.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.pref, rootKey);
        EditTextPreference updatePeriodEditText =
                findPreference(getString(R.string.edit_text_period_preferences_key));
        if (updatePeriodEditText != null) {
            updatePeriodEditText.setOnBindEditTextListener(
                    editText -> {
                        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                        editText.setTextColor(ContextCompat
                                .getColor(requireActivity(), R.color.preference_color_state_list));
                    });
        }
    }
}