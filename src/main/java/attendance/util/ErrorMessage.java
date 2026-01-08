package attendance.util;

public enum ErrorMessage {

    FILE_LOAD_EXCEPTION("파일 로드 중에 문제가 생겼습니다."),
    WRONG_INPUT("잘못된 형식을 입력하였습니다."),
    NOT_CONTAINS_MEMBER("등록되지 않은 닉네임입니다."),
    NOT_WORKING_DAY("12월 %d일 %s은 등교일이 아닙니다."),
    NOT_AVAILABLE_MODIFY("아직 수정할 수 없습니다."),
    NOT_CAMPUS_TIME("캠퍼스 운영 시간에만 출석이 가능합니다."),
    ALREADY_ATTEND("이미 출석을 확인하였습니다. 필요한 경우 수정 기능을 이용해 주세요.")

    ;

    private final static String PREFIX = "[ERROR] ";
    private final String message;

    ErrorMessage(String message) {
        this.message = PREFIX + message;
    }

    public String getMessage() {
        return message;
    }

    public String format(Object... args) {
        return String.format(message, args);
    }
}
