package process;

import files.FileValidator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CaesarCipherFileProcessor {
    private final EncryptProcessor encryptProcessor = new EncryptProcessor();
    private final BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
    private final String ENCRYPT_TEXT = "encrypt";

    public void processFileWithKey(String operation) {
        String inputPath = getPathFromUser("Введите путь к " +
                (operation.equals(ENCRYPT_TEXT) ? "незашифрованному" : "зашифрованному") + " файлу...");
        String outputPath = getPathFromUser("Введите путь куда сохранить " +
                (operation.equals(ENCRYPT_TEXT) ? "зашифрованный" : "расшифрованный") + " файл...");

        System.out.println("Введите ключ:");
        int key = readIntFromUser();

        try (BufferedReader fileReader = Files.newBufferedReader(Paths.get(inputPath));
             BufferedWriter fileWriter = Files.newBufferedWriter(Paths.get(outputPath))) {

            String line;
            StringBuilder encryptedDecryptedLine = new StringBuilder();
            while ((line = fileReader.readLine()) != null) {
                encryptedDecryptedLine.setLength(0);
                encryptedDecryptedLine.append(operation.equals(ENCRYPT_TEXT)
                        ? encryptFile(line, key)
                        : decryptFile(line, key));

                fileWriter.write(encryptedDecryptedLine.toString());
                fileWriter.newLine();
            }

            System.out.print(operation.equals(ENCRYPT_TEXT) ? "Шифрование" : "Дешифрование");
            System.out.println(" завершено. Результат записан в файл: " + outputPath + "\n");

        } catch (IOException e) {
            System.out.println("Ошибка при обработке файлов: " + e.getMessage());
        }
    }

    public String encryptFile(String line, int key) {
        return encryptProcessor.codingProcess(line, key);
    }

    public String decryptFile(String line, int key) {
        return encryptProcessor.codingProcess(line, key * (-1));
    }

    public String getPathFromUser(String prompt) {
        System.out.println(prompt);
        try {
            return new FileValidator().validatePath(consoleReader.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int readIntFromUser() {
        try {
            return Integer.parseInt(consoleReader.readLine());
        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }
}



