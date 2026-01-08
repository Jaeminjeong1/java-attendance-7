package attendance.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {

    private static final String INPUT_NAME = "닉네임을 입력해 주세요.";
    private static final String INPUT_TIME = "등교 시간을 입력해 주세요.";
    private static final String INPUT_MODIFY_NAME = "출석을 수정하려는 크루의 닉네임을 입력해 주세요.";
    private static final String INPUT_MODIFY_DAY = "수정하려는 날짜(일)를 입력해 주세요.";
    private static final String INPUT_MODIFY_TIME = "언제로 변경하겠습니까?";

    private InputView() {
    }

    public static String readMenu() {
        return Console.readLine();
    }

    public static String readName() {
        System.out.println(INPUT_NAME);
        return Console.readLine();
    }

    public static String readTime() {
        System.out.println(INPUT_TIME);
        return Console.readLine();
    }

    public static String readModifyName() {
        System.out.println(INPUT_MODIFY_NAME);
        return Console.readLine();
    }

    public static String readModifyDay() {
        System.out.println(INPUT_MODIFY_DAY);
        return Console.readLine();
    }

    public static String readModifyTime() {
        System.out.println(INPUT_MODIFY_TIME);
        return Console.readLine();
    }
}
