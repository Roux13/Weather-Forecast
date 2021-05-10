package ru.nehodov.weatherforecast

import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import ru.nehodov.weatherforecast.utils.WeatherUtil
import java.time.Instant
import java.time.temporal.ChronoUnit

@RunWith(MockitoJUnitRunner::class)
class WeatherUtilTest {
    @Test
    fun formatTemperatureWhenTempBeforeIs10_05ThenPlus10() {
        val tempBefore = "10.05"
        val expected = "+10"
        assertEquals(expected, WeatherUtil.formatTemp(tempBefore))
    }

    @Test
    fun formatTemperatureWhenTempBeforeIsMinus10_05ThenMinus10() {
        val tempBefore = "-10.05"
        val expected = "-10"
        assertEquals(expected, WeatherUtil.formatTemp(tempBefore))
    }

    @Test
    fun formatTemperatureWhenTempBeforeIs30_1ThenPlus30() {
        val tempBefore = "30.1"
        val expected = "+30"
        assertEquals(expected, WeatherUtil.formatTemp(tempBefore))
    }

    @Test
    fun formatTemperatureWhenTempBeforeIs20_5ThenPlus21() {
        val tempBefore = "20.5"
        val expected = "+21"
        assertEquals(expected, WeatherUtil.formatTemp(tempBefore))
    }

    @Test
    fun formatTemperatureWhenWrongInputThenNaN() {
        val tempBefore = "A"
        val expected = ""
        assertEquals(expected, WeatherUtil.formatTemp(tempBefore))
    }

    @Test
    fun formatTemperatureWhenDoubleMinusThenNaN() {
        val tempBefore = "--5"
        val expected = ""
        assertEquals(expected, WeatherUtil.formatTemp(tempBefore))
    }

    @Test
    fun formatTempWhenArgIsNull() {
        val tempBefore: String? = null
        val expected = ""
        assertEquals(expected, WeatherUtil.formatTemp(tempBefore))
    }

    @Test
    fun formatTempWhenArgIsEmptyLine() {
        val tempBefore = ""
        val expected = ""
        assertEquals(expected, WeatherUtil.formatTemp(tempBefore))
    }

    @Test
    fun formatDateWhenDateIsNowThenToday() {
        val now = Instant.now().epochSecond.toString()
        val expected = "Today"
        assertEquals(expected, WeatherUtil.formatDate(now))
    }

    @Test
    fun formatDateWhenDateIsTomorrowThenTomorrow() {
        val tomorrow =
            Instant.now().plus(1, ChronoUnit.DAYS).epochSecond.toString()
        val expected = "Tomorrow"
        assertEquals(expected, WeatherUtil.formatDate(tomorrow))
    }

    @Test
    fun formatDateWhenDateIs03_09_2020Tomorrow() {
        val date = "1599156000"
        val expected = "03 09 Thu"
        assertEquals(expected, WeatherUtil.formatDate(date))
    }

    @Test
    fun formatDateWhenArgIsNull() {
        val unixTime: String? = null
        val expected = ""
        assertEquals(expected, WeatherUtil.formatDate(unixTime))
    }

    @Test
    fun formatDateWhenArgHasLetter() {
        val unixTime = "12h12"
        val expected = ""
        assertEquals(expected, WeatherUtil.formatDate(unixTime))
    }

    @Test
    fun formatDateWhenArgIsEmptyLine() {
        val unixTime = ""
        val expected = ""
        assertEquals(expected, WeatherUtil.formatDate(unixTime))
    }
}