package daisukeclinic;

import daisukeclinic.utils.ConsoleUtility;
import daisukeclinic.utils.SaveUtility;
import daisukeclinic.view.AppConsole;

public class Main {

    public Main() {
        ConsoleUtility.clearScreen();
        SaveUtility.loadAll();
        splashScreen();
        AppConsole.getInstance().start();
    }

    public void splashScreen() {
        System.out.println(" ____        _           _         ____ _ _       _      ____            \n" + //
                "|  _ \\  __ _(_)___ _   _| | _____ / ___| (_)_ __ (_) ___|  _ \\ _ __ ___  \n" + //
                "| | | |/ _` | / __| | | | |/ / _ \\ |   | | | '_ \\| |/ __| |_) | '__/ _ \\ \n" + //
                "| |_| | (_| | \\__ \\ |_| |   <  __/ |___| | | | | | | (__|  __/| | | (_) |\n" + //
                "|____/ \\__,_|_|___/\\__,_|_|\\_\\___|\\____|_|_|_| |_|_|\\___|_|   |_|  \\___/ \n\n" + //
                "                                                                         ");
        ConsoleUtility.pressAnyKeyToContinue();
    }

    public static void main(String[] args) {
        new Main();
    }
}