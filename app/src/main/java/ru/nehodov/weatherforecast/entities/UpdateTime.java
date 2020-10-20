package ru.nehodov.weatherforecast.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;
import java.util.StringJoiner;

@Entity
public class UpdateTime {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String time;

    public UpdateTime(long id, String time) {
        this.id = id;
        this.time = time;
    }

    @Ignore
    public UpdateTime(String time) {
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UpdateTime that = (UpdateTime) o;
        return id == that.id && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, time);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UpdateTime.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("time='" + time + "'")
                .toString();
    }
}
