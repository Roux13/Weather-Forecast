package ru.nehodov.weatherforecast.settings

import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import ru.nehodov.weatherforecast.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.pref, rootKey)
        val updatePeriodEditText: EditTextPreference? =
            findPreference(getString(R.string.edit_text_period_preferences_key))
        updatePeriodEditText?.setOnBindEditTextListener { editText: EditText ->
            editText.inputType = InputType.TYPE_CLASS_NUMBER
            editText.setTextColor(
                ContextCompat
                    .getColor(requireActivity(), R.color.preference_color_state_list)
            )
        }
    }
}