package attendance.service;

import attendance.domain.Attendance;
import attendance.domain.AttendanceSummary;
import attendance.domain.Crew;
import attendance.domain.Day;
import attendance.domain.Holiday;
import attendance.domain.Member;
import attendance.domain.RiskLevel;
import attendance.domain.Status;
import attendance.dto.AttendanceUpdateResult;
import attendance.dto.CrewAttendanceResult;
import attendance.dto.RiskCrewResult;
import attendance.util.ErrorMessage;
import attendance.util.Parser;
import attendance.util.Validator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class AttendanceService {
    private static final LocalDate START_DATE = LocalDate.of(2024, 12, 1);
    private Crew crew;

    public void setAttendances(List<String> fileDatas) {
        Map<String, Member> members = createMembers(fileDatas);
        crew = new Crew(new ArrayList<>(members.values()));
        for (String fileData : fileDatas) {
            Attendance attendance = parseAttendance(fileData);
            members.get(attendance.getName()).addAttendance(attendance.getDate(), attendance.getTime());
        }
    }

    public void validateCrewName(String name) {
        if (!crew.contains(name)) {
            throw new IllegalArgumentException(ErrorMessage.NOT_CONTAINS_MEMBER.getMessage());
        }
    }

    public void validateWorkingDay(LocalDate date) {
        if (!Day.isWeekday(date) || Holiday.isHoliday(date)) {
            throw new IllegalArgumentException(
                ErrorMessage.NOT_WORKING_DAY.format(date.getDayOfMonth(), Day.koreanName(date))
            );
        }
    }

    public Attendance checkIn(String name, String timeInput, LocalDate today) {
        LocalTime time = parseTime(timeInput);
        Validator.validateCampusTime(time);
        Member member = findMember(name);
        validateAlreadyAttended(member, today);
        member.addAttendance(today, time);
        return Attendance.of(name, today, time);
    }

    public LocalDate parseUpdateDate(String dayInput, LocalDate today) {
        Validator.validateEmptyInput(dayInput);
        Validator.validateNumberFormat(dayInput);
        LocalDate date = parseDayOfMonth(dayInput);
        validateWorkingDay(date);
        Validator.validatePastDate(date, today);
        return date;
    }

    public AttendanceUpdateResult updateAttendance(String name, LocalDate date, String timeInput) {
        LocalTime time = parseTime(timeInput);
        Validator.validateCampusTime(time);
        Member member = findMember(name);
        Attendance before = createBeforeRecord(member, date);
        Attendance after = member.updateAttendance(date, time);
        return new AttendanceUpdateResult(before, Attendance.of(name, date, after.getTime()));
    }

    public CrewAttendanceResult getCrewRecords(String name, LocalDate today) {
        Member member = findMember(name);
        List<Attendance> records = buildRecords(member, today.minusDays(1));
        AttendanceSummary summary = summarize(records);
        updateRiskLevel(member, summary);
        return new CrewAttendanceResult(name, records, summary);
    }

    public List<RiskCrewResult> getRiskCrews(LocalDate today) {
        LocalDate endDate = today.minusDays(1);
        List<RiskCrewResult> results = new ArrayList<>();
        for (Member member : crew.getMembers()) {
            List<Attendance> records = buildRecords(member, endDate);
            AttendanceSummary summary = summarize(records);
            updateRiskLevel(member, summary);
            addRiskResult(results, member, summary);
        }
        results.sort(riskComparator());
        return results;
    }

    private Map<String, Member> createMembers(List<String> fileDatas) {
        Set<String> names = extractCrew(fileDatas);
        Map<String, Member> members = new HashMap<>();
        for (String name : names) {
            members.put(name, new Member(name));
        }
        return members;
    }

    private Set<String> extractCrew(List<String> fileDatas) {
        java.util.Set<String> names = new java.util.HashSet<>();
        for (String fileData : fileDatas) {
            List<String> parsed = Parser.parse(fileData);
            names.add(parsed.getFirst());
        }
        return names;
    }

    private Attendance parseAttendance(String fileData) {
        List<String> parsed = Parser.parse(fileData);
        String name = parsed.getFirst();
        List<String> dateTime = Parser.parseDateTime(parsed.get(1));
        LocalDate date = parseDate(dateTime.getFirst());
        LocalTime time = parseTime(dateTime.get(1));
        return Attendance.of(name, date, time);
    }

    private Attendance createBeforeRecord(Member member, LocalDate date) {
        Optional<Attendance> existing = member.findAttendance(date);
        if (existing.isEmpty()) {
            return Attendance.of(member.getName(), date, null);
        }
        Attendance attendance = existing.get();
        return Attendance.of(attendance.getName(), date, attendance.getTime());
    }

    private List<Attendance> buildRecords(Member member, LocalDate endDate) {
        List<Attendance> records = new ArrayList<>();
        LocalDate date = START_DATE;
        while (!date.isAfter(endDate)) {
            if (Day.isWeekday(date) && !Holiday.isHoliday(date)) {
                records.add(findOrCreateRecord(member, date));
            }
            date = date.plusDays(1);
        }
        return records;
    }

    private Attendance findOrCreateRecord(Member member, LocalDate date) {
        Optional<Attendance> existing = member.findAttendance(date);
        if (existing.isPresent()) {
            Attendance attendance = existing.get();
            return Attendance.of(attendance.getName(), date, attendance.getTime());
        }
        return Attendance.of(member.getName(), date, null);
    }

    private AttendanceSummary summarize(List<Attendance> records) {
        int attendanceCount = countByStatus(records, Status.ATTENDANCE);
        int lateCount = countByStatus(records, Status.LATE);
        int absenceCount = countByStatus(records, Status.ABSENCE);
        RiskLevel riskLevel = RiskLevel.from(absenceCount, lateCount);
        return new AttendanceSummary(attendanceCount, lateCount, absenceCount, riskLevel);
    }

    private int countByStatus(List<Attendance> records, Status status) {
        int count = 0;
        for (Attendance record : records) {
            if (record.getStatus() == status) {
                count++;
            }
        }
        return count;
    }

    private void updateRiskLevel(Member member, AttendanceSummary summary) {
        member.updateRiskLevel(summary.riskLevel());
    }

    private void addRiskResult(List<RiskCrewResult> results, Member member, AttendanceSummary summary) {
        if (summary.riskLevel() == RiskLevel.NONE) {
            return;
        }
        int totalAbsence = summary.absenceCount() + (summary.lateCount() / 3);
        results.add(new RiskCrewResult(member.getName(), summary, totalAbsence));
    }

    private Comparator<RiskCrewResult> riskComparator() {
        return Comparator.comparing(RiskCrewResult::riskRank)
            .thenComparing(RiskCrewResult::totalAbsence, Comparator.reverseOrder())
            .thenComparing(RiskCrewResult::name);
    }

    private void validateAlreadyAttended(Member member, LocalDate today) {
        Optional<Attendance> existing = member.findAttendance(today);
        if (existing.isPresent()) {
            throw new IllegalArgumentException(ErrorMessage.ALREADY_ATTEND.getMessage());
        }
    }

    private Member findMember(String name) {
        return crew.findMember(name)
            .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NOT_CONTAINS_MEMBER.getMessage()));
    }

    private LocalTime parseTime(String input) {
        try {
            return LocalTime.parse(input);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(ErrorMessage.WRONG_INPUT.getMessage());
        }
    }

    private LocalDate parseDate(String input) {
        try {
            return LocalDate.parse(input);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(ErrorMessage.WRONG_INPUT.getMessage());
        }
    }

    private LocalDate parseDayOfMonth(String input) {
        try {
            int day = Integer.parseInt(input);
            return LocalDate.of(2024, 12, day);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException(ErrorMessage.WRONG_INPUT.getMessage());
        }
    }
}
