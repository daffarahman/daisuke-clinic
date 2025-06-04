package daisukeclinic.utils;

public class Utility {
    public static int getMaxStringLengthFromArray(String[] arr) {
        int res = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].length() > res) {
                res = arr[i].length();
            }
        }
        return res;
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
        Utility.printChars('═', titleWidth);
        System.out.println("╗");

        System.out.print("║");
        Utility.printChars(' ', padding);
        System.out.print(title);
        Utility.printChars(' ', titleWidth - title.length() - padding);
        System.out.println("║");

        System.out.print("╚");
        Utility.printChars('═', titleWidth);
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
