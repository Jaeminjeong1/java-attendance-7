package attendance.controller;

import attendance.service.AttendanceService;

public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    public void start() {
        // 출석부 파일 로드

        // 오늘 날짜 구한 후, 프로그램 메인화면 출력

        // 번호 입력 받고, 각 기능 시작 - 프로그램 종료까지
    }
}
