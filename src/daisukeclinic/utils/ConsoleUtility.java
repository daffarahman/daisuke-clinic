package daisukeclinic.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ConsoleUtility {
    private static Scanner scanner = new Scanner(System.in);

    public static int getIntPromptInput(String promptMessage) {
        boolean looping = true;
        int result = 0;
        while (looping) {
            System.out.print(promptMessage);
            try {
                result = scanner.nextInt();
                looping = false;
            } catch (Exception e) {
                continue;
            } finally {
                scanner.nextLine();
            }
        }
        return result;
    }

    public static String getPhoneNumberPromptInput(String promptMessage) {
        boolean looping = true;
        String result = "";
        // Starts with +62 or 0
        String phoneRegex = "^\\+?[0-9]{10,13}$";

        while (looping) {
            System.out.print(promptMessage);
            result = scanner.nextLine().trim();

            result = result.replaceAll("[\\s-]", "");

            if (result.matches(phoneRegex)) {
                if (result.startsWith("0")) {
                    result = "+62" + result.substring(1);
                }
                looping = false;
            } else {
                System.out.println("Invalid phone number format! Please enter 10-13 digits.");
                System.out.println("Example: +6281234567890 or 081234567890");
            }
        }
        return result;
    }

    public static LocalDateTime getDateTimePromptInput(String promptMessage) {
        boolean looping = true;
        LocalDateTime result = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        while (looping) {
            System.out.print(promptMessage + " (Format: yyyy-MM-dd HH:mm): ");
            String input = scanner.nextLine().trim();

            try {
                result = LocalDateTime.parse(input, formatter);
                looping = false;
            } catch (Exception e) {
                System.out.println("Invalid date time format! Please use yyyy-MM-dd HH:mm");
                System.out.println("Example: 2023-12-15 14:30");
            }
        }

        return result;
    }

    public static String getStringPromptInput(String promptMessage) {
        System.out.print(promptMessage);
        return scanner.nextLine().strip();
    }

    public static void printChars(char c, int length) {
        for (int i = 0; i < length; i++) {
            System.out.print(c);
        }
    }

    public static void printTitle(String title) {
        int titleWidth = title.length() + 16;
        int padding = (titleWidth - title.length()) / 2;

        System.out.print("╔");
        ConsoleUtility.printChars('═', titleWidth);
        System.out.println("╗");

        System.out.print("║");
        ConsoleUtility.printChars(' ', padding);
        System.out.print(title);
        ConsoleUtility.printChars(' ', titleWidth - title.length() - padding);
        System.out.println("║");

        System.out.print("╚");
        ConsoleUtility.printChars('═', titleWidth);
        System.out.println("╝");
    }

    public static void clearScreen() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            ProcessBuilder processBuilder;

            if (os.contains("win"))
                processBuilder = new ProcessBuilder("cmd", "/c", "cls");
            else
                processBuilder = new ProcessBuilder("clear");

            processBuilder.inheritIO();
            Process process = processBuilder.start();

            process.waitFor();
        } catch (Exception e) {
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }

    public static void pressAnyKeyToContinue() {
        System.out.println("Press Enter to continue...");
        try {
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
