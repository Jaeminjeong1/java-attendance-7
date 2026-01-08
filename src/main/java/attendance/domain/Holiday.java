package attendance.domain;

import java.time.LocalDate;
import java.util.Set;

public final class Holiday {
    private static final Set<LocalDate> HOLIDAYS = Set.of(
        LocalDate.of(2024, 12, 25)
    );

    private Holiday() {
    }

    public static boolean isHoliday(LocalDate date) {
        return HOLIDAYS.contains(date);
    }
}
