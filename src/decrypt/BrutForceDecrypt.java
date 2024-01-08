package decrypt;

import alphabet.Alphabet;
import process.CaesarCipherFileProcessor;
import process.EncryptProcessor;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BrutForceDecrypt {
    Alphabet alphabet = new Alphabet();
    private final EncryptProcessor encryptProcessor = new EncryptProcessor();
    private final CaesarCipherFileProcessor processor = new CaesarCipherFileProcessor();
    private final int MAX_SHIFT = alphabet.getSize();

    public void brutforce() {
        try {
            String encryptedFilePath = getPathFromUser("Введите путь к зашифрованному файлу...");
            String encryptedText = readFromFile(encryptedFilePath);
            String referenceFilePath = getPathFromUser("Введите путь к файлу с примером текста...");
            String referenceText = readFromFile(referenceFilePath);

            int shift = findBestShift(encryptedText, referenceText);
            System.out.println("Сдвиг: "+ shift);

            String decryptedText = processor.decryptFile(encryptedText, shift);

            String decryptedFilePath = getPathFromUser("Введите путь куда сохранить расшифрованный файл...");

            try (BufferedWriter fileWriter = Files.newBufferedWriter(Paths.get(decryptedFilePath))) {
                fileWriter.write(decryptedText);
                fileWriter.newLine();
            }
            System.out.println("Расшифрованный текст сохранен в " + decryptedFilePath + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getPathFromUser(String message) {
        return processor.getPathFromUser(message);
    }

    private static String readFromFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            content.setLength(0);
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line);
            }
        }
        return content.toString();
    }

    /*
    Этот метод используется для нахождения оптимального сдвига
    (количество позиций, на которое каждый символ в зашифрованном тексте был сдвинут
    относительно оригинального текста) при использовании шифра Цезаря.
     */
    private int findBestShift(String encryptedText, String referenceText) {
        //Проверка деления на 0
        if (referenceText.isEmpty()) {
            throw new IllegalArgumentException("Файл с примером текста не может быть пустым!!!");
        }
        // maxMatchingCount - максимальное количество совпадающих символов между зашифрованным текстом
        // и текстом-примером при определенном сдвиге.
        int maxMatchingCount = 0;
        // bestShift - переменная хранит самый оптимальный сдвиг.
        int bestShift = 0;
        //Цикл по кол-ву всех символов в ALPHABET для проверки всех возможных сдвигов
        for (int shift = 0; shift < MAX_SHIFT; shift++) {
            int matchingCount = 0;
            //На каждом шаге цикла создается новая строка shiftedReferenceText,
            // представляющая собой текст-пример, сдвинутый на текущий сдвиг.
            String shiftedReferenceText = processor.encryptFile(referenceText, shift);

            // Цикл для сравнения каждого символа в зашифрованном тексте с соответствующим символом
            // в сдвинутом тексте-примере.
            for (int i = 0; i < encryptedText.length(); i++) {
                if (encryptedText.charAt(i) == shiftedReferenceText.charAt(i % referenceText.length())) {
                    matchingCount++;
                }
            }
            //Если текущий сдвиг дает большее количество совпадающих символов,
            // чем предыдущий лучший сдвиг, обновляются значения maxMatchingCount и bestShift.
            if (matchingCount > maxMatchingCount) {
                maxMatchingCount = matchingCount;
                bestShift = shift;
            }
        }

        return bestShift;
    }
}
