package attendance.util;

import java.util.Arrays;
import java.util.List;

public class Parser {

    private static final String DELIMITER = ",";
    private static final String DELIMITER_DATE_TIME = " ";


    private Parser() {
    }

    public static List<String> parseDateTime(String input) {
        validateInput(input);

        List<String> tokens = Arrays.asList(input.trim().split(DELIMITER_DATE_TIME));

        validateTokens(tokens);

        return tokens;
    }

    // List<String> 반환
    public static List<String> parse(String input) {
        validateInput(input);

        List<String> tokens = Arrays.asList(input.trim().split(DELIMITER));

        validateTokens(tokens);

        return tokens;
    }

    private static void validateInput(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 입력값이 비어있습니다.");
        }
    }



    private static void validateTokens(List<String> tokens) {
        for (String token : tokens) {
            if (token == null || token.isBlank()) {
                throw new IllegalArgumentException(
                        "[ERROR] 파싱된 값 중 공백이거나 비어 있는 값이 존재합니다."
                );
            }
        }
    }
}

