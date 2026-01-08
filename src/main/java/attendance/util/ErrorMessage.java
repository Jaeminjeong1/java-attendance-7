package attendance.util;

public enum ErrorMessage {

    INPUT_ERROR("유효하지 않은 입력 값입니다. 다시 입력해 주세요."),
    TYPE_ERROR("타입이 일치하지 않습니다. 다시 입력해 주세요."),
    NUMBER_FORMAT_ERROR("숫자형식이 아닙니다. 다시 입력해 주세요."),
    FILE_LOAD_EXCEPTION("파일 로드 중에 문제가 생겼습니다.")

    ;

    private final static String PREFIX = "[ERROR] ";
    private final String message;

    ErrorMessage(String message) {
        this.message = PREFIX + message;
    }

    public String getMessage() {
        return message;
    }
}
