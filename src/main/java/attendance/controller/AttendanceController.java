package attendance.controller;

import attendance.service.AttendanceService;
import attendance.dto.AttendanceUpdateResult;
import attendance.dto.CrewAttendanceResult;
import attendance.dto.RiskCrewResult;
import attendance.util.FileLoader;
import attendance.util.ErrorMessage;
import attendance.util.Validator;
import attendance.view.InputView;
import attendance.view.OutputView;
import camp.nextstep.edu.missionutils.DateTimes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    public void start() {
        loadData();
        runMenuLoop();
    }

    private void loadData() {
        List<String> fileData = FileLoader.loadFile();
        attendanceService.setAttendances(fileData);
    }

    private void runMenuLoop() {
        while (true) {
            LocalDateTime today = DateTimes.now();
            OutputView.printMenu(today.toLocalDate());
            String menu = InputView.readMenu();
            Validator.validateMenuNumber(menu);
            if ("Q".equalsIgnoreCase(menu)) {
                return;
            }
            int selected = Integer.parseInt(menu);
            runFunction(selected, today);
        }
    }

    private void runFunction(int selected, LocalDateTime today) {
        if (selected == 1) {
            checkIn(today);
            return;
        }
        if (selected == 2) {
            modifyAttendance(today);
            return;
        }
        if (selected == 3) {
            showCrewRecords(today);
            return;
        }
        if (selected == 4) {
            showRiskCrews(today);
            return;
        }
        throw new IllegalArgumentException(ErrorMessage.WRONG_INPUT.getMessage());
    }

    private void checkIn(LocalDateTime today) {
        attendanceService.validateWorkingDay(today.toLocalDate());
        String name = InputView.readName();
        attendanceService.validateCrewName(name);
        String timeInput = InputView.readTime();
        OutputView.printAttendanceResult(
            attendanceService.checkIn(name, timeInput, today.toLocalDate())
        );
    }

    private void modifyAttendance(LocalDateTime today) {
        String name = InputView.readModifyName();
        attendanceService.validateCrewName(name);
        String dayInput = InputView.readModifyDay();
        LocalDate date = attendanceService.parseUpdateDate(dayInput, today.toLocalDate());
        String timeInput = InputView.readModifyTime();
        AttendanceUpdateResult result = attendanceService.updateAttendance(name, date, timeInput);
        OutputView.printUpdateResult(result);
    }

    private void showCrewRecords(LocalDateTime today) {
        String name = InputView.readName();
        attendanceService.validateCrewName(name);
        CrewAttendanceResult result = attendanceService.getCrewRecords(name, today.toLocalDate());
        OutputView.printCrewRecords(result);
    }

    private void showRiskCrews(LocalDateTime today) {
        List<RiskCrewResult> results = attendanceService.getRiskCrews(today.toLocalDate());
        OutputView.printRiskCrews(results);
    }
}
