package ru.nehodov.weatherforecast;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import ru.nehodov.weatherforecast.utils.WeatherUtilKot;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class WeatherUtilTest {

    @Test
    public void formatTemperatureWhenTempBeforeIs10_05ThenPlus10() {
        String tempBefore = "10.05";
        String expected = "+10";

        assertEquals(expected, WeatherUtilKot.formatTemp(tempBefore));
    }

    @Test
    public void formatTemperatureWhenTempBeforeIsMinus10_05ThenMinus10() {
        String tempBefore = "-10.05";
        String expected = "-10";

        assertEquals(expected, WeatherUtilKot.formatTemp(tempBefore));
    }

    @Test
    public void formatTemperatureWhenTempBeforeIs30_1ThenPlus30() {
        String tempBefore = "30.1";
        String expected = "+30";

        assertEquals(expected, WeatherUtilKot.formatTemp(tempBefore));
    }

    @Test
    public void formatTemperatureWhenTempBeforeIs20_5ThenPlus21() {
        String tempBefore = "20.5";
        String expected = "+21";

        assertEquals(expected, WeatherUtilKot.formatTemp(tempBefore));
    }

    @Test
    public void formatTemperatureWhenWrongInputThenNaN() {
        String tempBefore = "A";
        String expected = "";

        assertEquals(expected, WeatherUtilKot.formatTemp(tempBefore));
    }

    @Test
    public void formatTemperatureWhenDoubleMinusThenNaN() {
        String tempBefore = "--5";
        String expected = "";

        assertEquals(expected, WeatherUtilKot.formatTemp(tempBefore));
    }

    @Test
    public void formatTempWhenArgIsNull() {
        String tempBefore = null;
        String expected = "";

        assertEquals(expected, WeatherUtilKot.formatTemp(tempBefore));
    }

    @Test
    public void formatTempWhenArgIsEmptyLine() {
        String tempBefore = "";
        String expected = "";

        assertEquals(expected, WeatherUtilKot.formatTemp(tempBefore));
    }

    @Test
    public void formatDateWhenDateIsNowThenToday() {
        String now = String.valueOf(Instant.now().getEpochSecond());
        String expected = "Today";

        assertEquals(expected, WeatherUtilKot.formatDate(now));
    }

    @Test
    public void formatDateWhenDateIsTomorrowThenTomorrow() {
        String tomorrow = String.valueOf(
                Instant.now().plus(1, ChronoUnit.DAYS).getEpochSecond());
        String expected = "Tomorrow";

        assertEquals(expected, WeatherUtilKot.formatDate(tomorrow));
    }

    @Test
    public void formatDateWhenDateIs03_09_2020Tomorrow() {
        String date = "1599156000";
        String expected = "03 09 Thu";

        assertEquals(expected, WeatherUtilKot.formatDate(date));
    }

    @Test
    public void formatDateWhenArgIsNull() {
        String unixTime = null;
        String expected = "";

        assertEquals(expected, WeatherUtilKot.formatDate(unixTime));
    }

    @Test
    public void formatDateWhenArgHasLetter() {
        String unixTime = "12h12";
        String expected = "";

        assertEquals(expected, WeatherUtilKot.formatDate(unixTime));
    }

    @Test
    public void formatDateWhenArgIsEmptyLine() {
        String unixTime = "";
        String expected = "";

        assertEquals(expected, WeatherUtilKot.formatDate(unixTime));
    }
}