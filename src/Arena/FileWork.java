package Arena;

import java.io.*;
import java.util.Scanner;

public class FileWork {
    private static PrintStream filePrintStream;

    public static void writeToFile() {
        if (filePrintStream == null) {
            try {
                File file = new File("D:\\droids_buttle.txt");
                filePrintStream = new PrintStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        System.setOut(filePrintStream);
    }

    public static void chooseStream() {
        System.out.print("\n○ Оберіть куди записати битву:\n1 Консоль\n2 Файл \n❯ ");
        Scanner scanner = new Scanner(System.in);
        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1 -> System.out.println("\n=== Дані будуть виведені в консоль ===");
            case 2 -> {
                System.out.println("\n=== Дані виведені в файл ===");
                writeToFile();
            }
            default -> System.out.println("\n=== Дані будуть виведені в консоль ===");
        }
    }

    public static void restoreConsoleStream() {
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
    }
}
