package attendance.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public enum Status {
    ATTENDANCE("출석"),
    LATE("지각"),
    ABSENCE("결석");

    private final String label;

    Status(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static Status from(LocalDate date, LocalTime time) {
        if (time == null) {
            return ABSENCE;
        }
        LocalTime start = Day.from(date).getStartTime();
        if (!time.isAfter(start.plusMinutes(5))) {
            return ATTENDANCE;
        }
        if (!time.isAfter(start.plusMinutes(30))) {
            return LATE;
        }
        return ABSENCE;
    }
}
