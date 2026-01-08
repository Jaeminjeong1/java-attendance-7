package attendance.service;

import attendance.domain.Attendance;
import attendance.domain.AttendanceRepository;
import attendance.util.Parser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AttendanceService {

    public void setAttendances(List<String> fileDatas) {
        Set<String> crew = new HashSet<>();

        for (String fileData : fileDatas) {
            List<String> parsed = Parser.parse(fileData);
            String name = parsed.getFirst();
            String dateTimes = parsed.get(1);
            crew.add(name);

            List<String> dateTime = Parser.parseDateTime(dateTimes);
            LocalDate date = LocalDate.parse(dateTime.getFirst());
            LocalTime time = LocalTime.parse(dateTime.get(1));
            Attendance attendance = Attendance.of(name, date, time);
            AttendanceRepository.add(attendance);
        }
    }


}
