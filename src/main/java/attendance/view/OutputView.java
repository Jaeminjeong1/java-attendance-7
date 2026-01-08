package attendance.view;

import attendance.domain.Attendance;
import attendance.domain.AttendanceSummary;
import attendance.domain.RiskLevel;
import attendance.dto.AttendanceUpdateResult;
import attendance.dto.CrewAttendanceResult;
import attendance.dto.RiskCrewResult;
import attendance.domain.Day;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OutputView {
    private static final String MENU_1 = "1. 출석 확인";
    private static final String MENU_2 = "2. 출석 수정";
    private static final String MENU_3 = "3. 크루별 출석 기록 확인";
    private static final String MENU_4 = "4. 제적 위험자 확인";
    private static final String MENU_Q = "Q. 종료";
    private static final String RECORD_TITLE = "이번 달 %s의 출석 기록입니다.";
    private static final String RISK_TITLE = "제적 위험자 조회 결과";

    private OutputView() {
    }

    public static void printMenu(LocalDate today) {
        System.out.println(formatToday(today));
        System.out.println(MENU_1);
        System.out.println(MENU_2);
        System.out.println(MENU_3);
        System.out.println(MENU_4);
        System.out.println(MENU_Q);
    }

    public static void printErrorMessage(IllegalArgumentException e) {
        System.out.println(e.getMessage());
    }

    public static void printAttendanceResult(Attendance record) {
        String line = String.format("%s %s (%s)",
            formatKoreanDate(record),
            formatTime(record.getTime()),
            record.getStatus().getLabel());
        System.out.println(line);
    }

    public static void printUpdateResult(AttendanceUpdateResult result) {
        Attendance before = result.before();
        Attendance after = result.after();
        String line = String.format("%s %s (%s) -> %s (%s) 수정 완료!",
            formatKoreanDate(before),
            formatTime(before.getTime()),
            before.getStatus().getLabel(),
            formatTime(after.getTime()),
            after.getStatus().getLabel());
        System.out.println(line);
    }

    public static void printCrewRecords(CrewAttendanceResult result) {
        System.out.println(String.format(RECORD_TITLE, result.name()));
        for (Attendance record : result.records()) {
            printAttendanceResult(record);
        }
        printSummary(result.summary());
    }

    public static void printRiskCrews(List<RiskCrewResult> results) {
        System.out.println(RISK_TITLE);
        for (RiskCrewResult result : results) {
            String line = String.format("- %s: 결석 %d회, 지각 %d회 (%s)",
                result.name(),
                result.summary().absenceCount(),
                result.summary().lateCount(),
                result.summary().riskLevel().getLabel());
            System.out.println(line);
        }
    }

    private static void printSummary(AttendanceSummary summary) {
        System.out.println(String.format("출석: %d회", summary.attendanceCount()));
        System.out.println(String.format("지각: %d회", summary.lateCount()));
        System.out.println(String.format("결석: %d회", summary.absenceCount()));
        RiskLevel level = summary.riskLevel();
        if (level == RiskLevel.NONE) {
            return;
        }
        System.out.println(String.format("%s 대상자입니다.", level.getLabel()));
    }

    private static String formatToday(LocalDate date) {
        return String.format("오늘은 12월 %d일 %s입니다. 기능을 선택해 주세요.",
            date.getDayOfMonth(), Day.koreanName(date));
    }

    private static String formatKoreanDate(Attendance attendance) {
        return String.format("12월 %02d일 %s", attendance.getDayOfMonth(), attendance.getDayName());
    }

    private static String formatTime(LocalTime time) {
        if (time == null) {
            return "--:--";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return time.format(formatter);
    }
}
