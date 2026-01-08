package attendance.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Attendances {
    private final List<Attendance> attendances;

    public Attendances() {
        this.attendances = new ArrayList<>();
    }

    public void add(Attendance attendance) {
        attendances.add(attendance);
    }

    public Optional<Attendance> findByDate(LocalDate date) {
        for (Attendance attendance : attendances) {
            if (attendance.getDate().equals(date)) {
                return Optional.of(attendance);
            }
        }
        return Optional.empty();
    }

    public List<Attendance> getAll() {
        return new ArrayList<>(attendances);
    }
}
