package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public enum Day {

    MON(DayOfWeek.MONDAY, LocalTime.of(13, 0)),
    TUE(DayOfWeek.TUESDAY, LocalTime.of(10, 0)),
    WED(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0)),
    THU(DayOfWeek.THURSDAY, LocalTime.of(10, 0)),
    FRI(DayOfWeek.FRIDAY, LocalTime.of(10, 0));

    private final DayOfWeek dayOfWeek;
    private final LocalTime startTime;
    private static final Map<Integer, String> KOREAN_DAYS = Map.of(
        1, "월요일",
        2, "화요일",
        3, "수요일",
        4, "목요일",
        5, "금요일",
        6, "토요일",
        7, "일요일"
    );

    Day(DayOfWeek dayOfWeek, LocalTime startTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public static Day from(LocalDate date) {
        for (Day day : values()) {
            if (day.dayOfWeek == date.getDayOfWeek()) {
                return day;
            }
        }
        throw new IllegalArgumentException();
    }

    public static boolean isWeekday(LocalDate date) {
        int day = date.getDayOfWeek().getValue();
        return day >= 1 && day <= 5;
    }

    public static String koreanName(LocalDate date) {
        String name = KOREAN_DAYS.get(date.getDayOfWeek().getValue());
        if (name == null) {
            throw new IllegalArgumentException();
        }
        return name;
    }
}
