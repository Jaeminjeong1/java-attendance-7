package attendance.dto;

import attendance.domain.AttendanceSummary;
import attendance.domain.RiskLevel;

public record RiskCrewResult(
    String name,
    AttendanceSummary summary,
    int totalAbsence
) {
    public int riskRank() {
        RiskLevel level = summary.riskLevel();
        if (level == RiskLevel.EXPULSION) {
            return 0;
        }
        if (level == RiskLevel.INTERVIEW) {
            return 1;
        }
        if (level == RiskLevel.WARNING) {
            return 2;
        }
        return 3;
    }
}
