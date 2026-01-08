package attendance.dto;

import attendance.domain.Attendance;

public record AttendanceUpdateResult(
    Attendance before,
    Attendance after
) {
}
