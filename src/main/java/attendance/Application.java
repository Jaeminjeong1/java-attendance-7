package attendance;

import attendance.controller.AttendanceController;
import attendance.service.AttendanceService;

public class Application {
    public static void main(String[] args) {
        AttendanceService attendanceService = new AttendanceService();
        AttendanceController attendanceController = new AttendanceController(attendanceService);
        attendanceController.start();
    }
}
