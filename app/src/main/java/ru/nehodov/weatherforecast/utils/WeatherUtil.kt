package ru.nehodov.weatherforecast.utils

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.*

class WeatherUtil {

    companion object {
        private val CORRECT_TEMP_FORMATS = Regex("^-?\\d+\\.?(\\d+)?$")
        private val CORRECT_UNIX_TIME_FORMAT = Regex("^\\d*\$")

        /**
         * Method rounds temperature up to an integer and if temperature is positive adds plus sign
         *
         * @param tempBefore Text temperature before formatting
         * @return formatted temperature
         */
        fun formatTemp(tempBefore: String?): String {
            var tempAfter = ""
            if (!tempBefore.isNullOrEmpty() && tempBefore.matches(CORRECT_TEMP_FORMATS)) {
                    val temp = tempBefore.toDouble()
                tempAfter = if (temp in -0.5..0.5) "0" else String.format(Locale.US, "%+.0f", temp)
            }
            return tempAfter
        }

        fun formatDate(unixTime: String?): String {
            if (!unixTime.isNullOrEmpty() && unixTime.matches(CORRECT_UNIX_TIME_FORMAT)) {
                val instant: Instant = Instant.ofEpochSecond(unixTime.toLong())
                val date: LocalDate = instant.atZone(ZoneId.systemDefault()).toLocalDate()
                val now: LocalDate = LocalDate.now()
                return when (date) {
                    now -> "Today"
                    now.plusDays(1) -> "Tomorrow"
                    else -> {
                        String.format(Locale.US, "%02d %02d %s",
                                date.dayOfMonth,
                                date.monthValue,
                                date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.US))
                    }
                }
            } else return ""
        }

        fun formatTime(unixTime: String?): String? {
            return if (unixTime.isNullOrEmpty() || !unixTime.matches(CORRECT_UNIX_TIME_FORMAT)) {
                ""
            } else {
                SimpleDateFormat("HH:mm", Locale.getDefault())
                        .format(Date(unixTime.toLong() * 1000))
            }
        }
    }
}