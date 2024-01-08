
import decrypt.BrutForceDecrypt;
import decrypt.StatisticAnalizer;
import process.CaesarCipherFileProcessor;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CaesarCipherFileProcessor caesarCipherFileProcessor = new CaesarCipherFileProcessor();
        BrutForceDecrypt brutForceDecrypt = new BrutForceDecrypt();
        StatisticAnalizer statisticAnalizer = new StatisticAnalizer();

        while (true) {
            printMenu();

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    caesarCipherFileProcessor.processFileWithKey("encrypt");
                    break;
                case "2":
                    caesarCipherFileProcessor.processFileWithKey("decrypt");
                    break;
                case "3":
                    brutForceDecrypt.brutforce();
                    break;
                case "4":
                    //statisticAnalizer.statisticAnalize();
                    System.out.println("Пока не работает");
                    break;
                case "5":
                    System.out.println("До свидания!");
                    System.exit(0);
                default:
                    System.out.println("Некорректный ввод. Пожалуйста, введите цифру от 1 до 4.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("""
                Введите цифру для нужного действия:
                 1. Зашифровать текст в файле с помощью ключа.
                 2. Расшифровать текст в файле с помощью ключа.
                 3. Расшифровка методом Brutforce.
                 4. Расшифровка методом статистического анализа.
                 5. Выход.""");
    }
}
