package attendance.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static attendance.util.ErrorMessage.FILE_LOAD_EXCEPTION;

public class FileLoader {

    private static final String FILE_PATH = "src/main/resources/attendances.csv";

    private FileLoader(){
    }

    public static List<String> loadFile() {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            readLinesFromFile(br, lines);
        } catch (IOException e) {
            throw new IllegalArgumentException(FILE_LOAD_EXCEPTION.getMessage());
        }
        return lines;
    }

    private static void readLinesFromFile(BufferedReader br, List<String> lines) throws IOException {
        br.readLine(); // 헤더 라인 스킵
        String line;
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
    }
}
