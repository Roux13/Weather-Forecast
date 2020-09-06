package ru.nehodov.weatherforecast.utils;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

import ru.nehodov.weatherforecast.entities.Daily;

public class DailyDiffUtilCallback extends DiffUtil.Callback {

    private final List<Daily> oldList;
    private final List<Daily> newList;

    public DailyDiffUtilCallback(List<Daily> oldList, List<Daily> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return true;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getDateTime()
                .equals(newList.get(newItemPosition).getDateTime())
                && oldList.get(oldItemPosition).getWeather()[0].getIcon().equals(
                newList.get(newItemPosition).getWeather()[0].getIcon())
                && oldList.get(oldItemPosition).getTemp()
                .equals(newList.get(newItemPosition).getTemp());
    }
}
