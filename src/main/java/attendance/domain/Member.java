package attendance.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class Member {
    private final String name;
    private final Attendances attendances;
    private RiskLevel riskLevel;

    public Member(String name) {
        this.name = name;
        this.attendances = new Attendances();
        this.riskLevel = RiskLevel.NONE;
    }

    public String getName() {
        return name;
    }

    public RiskLevel getRiskLevel() {
        return riskLevel;
    }

    public void updateRiskLevel(RiskLevel riskLevel) {
        this.riskLevel = riskLevel;
    }

    public void addAttendance(LocalDate date, LocalTime time) {
        attendances.add(Attendance.of(name, date, time));
    }

    public Optional<Attendance> findAttendance(LocalDate date) {
        return attendances.findByDate(date);
    }

    public Attendance updateAttendance(LocalDate date, LocalTime time) {
        Optional<Attendance> existing = attendances.findByDate(date);
        if (existing.isPresent()) {
            Attendance attendance = existing.get();
            attendance.updateTime(time);
            return attendance;
        }
        Attendance created = Attendance.of(name, date, time);
        attendances.add(created);
        return created;
    }

    public List<Attendance> getAttendances() {
        return attendances.getAll();
    }
}
