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
        this.status = null;
    }

    public static Attendance of(String name, LocalDate date, LocalTime time) {
        return new Attendance(name, date, time);
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public Status getStatus() {
        return status;
    }
}
