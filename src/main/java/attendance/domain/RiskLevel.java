package attendance.domain;

public enum RiskLevel {
    EXPULSION("제적"),
    INTERVIEW("면담"),
    WARNING("경고"),
    NONE("");

    private final String label;

    RiskLevel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static RiskLevel from(int absenceCount, int lateCount) {
        int totalAbsence = absenceCount + (lateCount / 3);
        if (totalAbsence > 5) {
            return EXPULSION;
        }
        if (totalAbsence >= 3) {
            return INTERVIEW;
        }
        if (totalAbsence >= 2) {
            return WARNING;
        }
        return NONE;
    }
}
