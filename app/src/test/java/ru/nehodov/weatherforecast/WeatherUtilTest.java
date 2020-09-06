package ru.nehodov.weatherforecast;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import ru.nehodov.weatherforecast.utils.WeatherUtil;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class WeatherUtilTest {

    @Test
    public void formatTemperatureWhenTempBeforeIs10_05ThenPlus10() {
        String tempBefore = "10.05";
        String expected = "+10";

        assertEquals(expected, WeatherUtil.formatTemp(tempBefore));
    }

    @Test
    public void formatTemperatureWhenTempBeforeIsMinus10_05ThenMinus10() {
        String tempBefore = "-10.05";
        String expected = "-10";

        assertEquals(expected, WeatherUtil.formatTemp(tempBefore));
    }

    @Test
    public void formatTemperatureWhenTempBeforeIs30_1ThenPlus30() {
        String tempBefore = "30.1";
        String expected = "+30";

        assertEquals(expected, WeatherUtil.formatTemp(tempBefore));
    }

    @Test
    public void formatTemperatureWhenTempBeforeIs20_5ThenPlus21() {
        String tempBefore = "20.5";
        String expected = "+21";

        assertEquals(expected, WeatherUtil.formatTemp(tempBefore));
    }

    @Test
    public void formatTemperatureWhenWrongInputThenNaN() {
        String tempBefore = "A";
        String expected = "NaN";

        assertEquals(expected, WeatherUtil.formatTemp(tempBefore));
    }

    @Test
    public void formatTemperatureWhenDoubleMinusThenNaN() {
        String tempBefore = "--5";
        String expected = "NaN";

        assertEquals(expected, WeatherUtil.formatTemp(tempBefore));
    }

    @Test
    public void formatDateWhenDateIsNowThenToday() {
        String now = String.valueOf(Instant.now().getEpochSecond());
        String expected = "Today";

        assertEquals(expected, WeatherUtil.formatDate(now));
    }

    @Test
    public void formatDateWhenDateIsTomorrowThenTomorrow() {
        String tomorrow = String.valueOf(
                Instant.now().plus(1, ChronoUnit.DAYS).getEpochSecond());
        String expected = "Tomorrow";

        assertEquals(expected, WeatherUtil.formatDate(tomorrow));
    }

    @Test
    public void formatDateWhenDateIs03_09_2020Tomorrow() {
        String date = "1599156000";
        String expected = "03 09 Thu";

        assertEquals(expected, WeatherUtil.formatDate(date));
    }
}