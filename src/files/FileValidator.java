package files;

import java.nio.file.FileSystems;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

public class FileValidator {

    public String validatePath(String filename) {
        String message = "Недопустимый путь файла. Вводите в формате ИмяДиска:\\ИмяФайла.txt";

        try {
            Path path = FileSystems.getDefault().getPath(filename);
            if (path.startsWith("\\Windows") || path.startsWith("\\Program Files")) {
                throw new RuntimeException(message);
            }
            return filename;
        } catch (InvalidPathException ex) {
            throw new RuntimeException(message);
        }
    }
}


