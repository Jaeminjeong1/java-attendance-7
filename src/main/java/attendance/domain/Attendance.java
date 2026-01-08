package attendance.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Attendance {

    private final String name;
    private final LocalDate date;
    private LocalTime time;
    private Status status;

    private Attendance(String name, LocalDate date, LocalTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.status = Status.from(date, time);
    }

    public static Attendance of(String name, LocalDate date, LocalTime time) {
        return new Attendance(name, date, time);
    }

    public String getName() {
        return name;
    }

    public java.time.LocalDate getDate() {
        return date;
    }

    public java.time.LocalTime getTime() {
        return time;
    }

    public Status getStatus() {
        return status;
    }

    public int getDayOfMonth() {
        return date.getDayOfMonth();
    }

    public int getHour() {
        if (time == null) {
            return -1;
        }
        return time.getHour();
    }

    public int getMinute() {
        if (time == null) {
            return -1;
        }
        return time.getMinute();
    }

    public String getDayName() {
        return Day.koreanName(date);
    }

    public void updateTime(java.time.LocalTime time) {
        this.time = time;
        this.status = Status.from(date, time);
    }
}
