package decrypt;

import alphabet.Alphabet;
import files.FileValidator;
import process.CaesarCipherFileProcessor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StatisticAnalizer {
    Alphabet alphabet = new Alphabet(); //48
    CaesarCipherFileProcessor cipherFileProcessor = new CaesarCipherFileProcessor();
    private final BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

    public void statisticAnalize() {
        try {
            String encryptedFilePath = getPathFromUser("Введите путь к зашифрованному файлу...");
            String encryptedText = readFromFile(encryptedFilePath);

            int bestShift = analyze(encryptedText);
            System.out.println(bestShift);

            String decryptedText = cipherFileProcessor.decryptFile(encryptedText, bestShift);
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

    public String getPathFromUser(String prompt) {
        System.out.println(prompt);
        try {
            return new FileValidator().validatePath(consoleReader.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    public int analyze(String encryptedText) {
        int[] encryptedFrequencies = calculateLetterFrequencies(encryptedText);

        // Ожидаемые частоты букв в русском языке
        double[] expectedFrequencies = {
                0.0798, 0.015, 0.0453, 0.017, 0.0298, 0.0845, 0.021, 0.0338, 0.0724, 0.0164, 0.0109,
                0.0426, 0.0329, 0.067, 0.1097, 0.0281, 0.0473, 0.0547, 0.0626, 0.0146, 0.0073, 0.0347,
                0.0262, 0.0201, 0.0164, 0.0004, 0.0167, 0.0169, 0.0145, 0.0077, 0.0036, 0.0149, 0.1104,
                0.0010, 0.0080, 0.0000, 0.0001, 0.0050, 0.0050, 0.0100, 0.0000, 0.0100, 0.0060,
                0.0006, 0.0000, 0.0001, 0.0008, 0.0001, 0.0900
        };

        int bestShift = 0;
        double minDifference = Double.MAX_VALUE;

        // Перебор всех возможных сдвигов
        for (int shift = 0; shift < alphabet.getSize(); shift++) {
            int[] decryptedFrequencies = calculateLetterFrequencies(cipherFileProcessor.decryptFile(encryptedText, shift));

            // Расчет разницы между частотами
            double difference = calculateFrequencyDifference(encryptedFrequencies, decryptedFrequencies,
                    expectedFrequencies);

            // Обновление лучшего сдвига, если разница меньше текущей минимальной
            if (difference < minDifference) {
                minDifference = difference;
                bestShift = shift;
            }
        }

        return bestShift;
    }

    private int[] calculateLetterFrequencies(String text) {
        int[] frequencies = new int[alphabet.getSize() + 1];

        for (char ch : text.toCharArray()) {
            int index = alphabet.getCharIndex(ch);
            if (index != -1) {
                frequencies[index]++;
            }
        }

        return frequencies;
    }

    private double calculateFrequencyDifference(int[] encryptedFrequencies, int[] decryptedFrequencies,
                                                double[] expectedFrequencies) {
        double difference = 0;

        for (int i = 0; i < alphabet.getSize(); i++) {
            double expectedFrequency = expectedFrequencies[i];
            double encryptedFrequency = (double) encryptedFrequencies[i] / encryptedFrequencies.length;
            double decryptedFrequency = (double) decryptedFrequencies[i] / decryptedFrequencies.length;

            difference += Math.abs(expectedFrequency - decryptedFrequency) * encryptedFrequency;
        }

        return difference;
    }
}


