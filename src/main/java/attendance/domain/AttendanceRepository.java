package attendance.domain;

import java.util.ArrayList;
import java.util.List;

public class AttendanceRepository {

    private final List<Attendance> attendances = new ArrayList<>();

    private AttendanceRepository() {
    }

    public static void add(Attendance attendance) {
        attendances.add(attendance);
    }
}
