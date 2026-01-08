package attendance.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.regex.Pattern;

public class Validator {

    private static final Pattern NUMBER_PATTERN = Pattern.compile("^\\d+$");
    private static final LocalTime CAMPUS_OPEN = LocalTime.of(8, 0);
    private static final LocalTime CAMPUS_CLOSE = LocalTime.of(23, 0);

    private Validator() {
    }

    // 빈값 검증
    public static void validateEmptyInput(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException(ErrorMessage.WRONG_INPUT.getMessage());
        }
    }

    // 숫자 형식 검증
    public static void validateNumberFormat(String input) {
        if (!NUMBER_PATTERN.matcher(input).matches()) {
            throw new IllegalArgumentException(ErrorMessage.WRONG_INPUT.getMessage());
        }
    }

    public static void validateMenuNumber(String input) {
        validateEmptyInput(input);
        if ("Q".equalsIgnoreCase(input)) {
            return;
        }
        validateNumberFormat(input);
        int value = Integer.parseInt(input);
        if (value < 1 || value > 4) {
            throw new IllegalArgumentException(ErrorMessage.WRONG_INPUT.getMessage());
        }
    }

    public static void validateCampusTime(LocalTime time) {
        if (time.isBefore(CAMPUS_OPEN)) {
            throw new IllegalArgumentException(ErrorMessage.NOT_CAMPUS_TIME.getMessage());
        }
        if (time.isAfter(CAMPUS_CLOSE)) {
            throw new IllegalArgumentException(ErrorMessage.NOT_CAMPUS_TIME.getMessage());
        }
    }

    public static void validatePastDate(LocalDate date, LocalDate today) {
        if (!date.isBefore(today)) {
            throw new IllegalArgumentException(ErrorMessage.NOT_AVAILABLE_MODIFY.getMessage());
        }
    }
}
