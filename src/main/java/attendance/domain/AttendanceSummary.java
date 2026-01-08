package attendance.domain;

public record AttendanceSummary(
    int attendanceCount,
    int lateCount,
    int absenceCount,
    RiskLevel riskLevel
) {
}
