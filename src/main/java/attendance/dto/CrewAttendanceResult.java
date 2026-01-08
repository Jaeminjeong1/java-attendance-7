package attendance.dto;

import attendance.domain.Attendance;
import attendance.domain.AttendanceSummary;

import java.util.List;

public record CrewAttendanceResult(
    String name,
    List<Attendance> records,
    AttendanceSummary summary
) {
}
