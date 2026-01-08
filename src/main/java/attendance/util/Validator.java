package attendance.util;

import java.util.regex.Pattern;

import static attendance.util.ErrorMessage.INPUT_ERROR;
import static attendance.util.ErrorMessage.NUMBER_FORMAT_ERROR;

public class Validator {

    private static final Pattern NUMBER_PATTERN = Pattern.compile("^\\d+$");
    private static final int MIN_NUM = 1; // 수정
    private static final int MAX_NUM = 10; // 수정

    private Validator() {
    }

    // 빈값 검증
    public static void validateEmptyInput(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException(INPUT_ERROR.getMessage());
        }
    }

    // 숫자 형식 검증
    public static void validateNumberFormat(String input) {
        if (!NUMBER_PATTERN.matcher(input).matches()) {
            throw new IllegalArgumentException(NUMBER_FORMAT_ERROR.getMessage());
        }
    }

    // 양수인지 검증
    public static void validatePositive(int value) {
        if (value <= 0) {
            throw new IllegalArgumentException("ERROR_MESSAGE");
        }
    }

    // 값 범위 검증
    public static void validateRange(int value) {
        if (value < MIN_NUM || value > MAX_NUM) {
            throw new IllegalArgumentException("ERROR_MESSAGE");
        }
    }
}

