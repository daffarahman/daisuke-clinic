public class Main {
    private Doctor[] doctors;

    public Main() {
        MenuList bootMenuList = new MenuList("DaisukeClinic Bootloader", 3);

        bootMenuList.addMenuItem(new MenuItem("Console Mode", null));
        bootMenuList.addMenuItem(new MenuItem("Graphical Mode", null));
        bootMenuList.addMenuItem(new MenuItem("Exit", () -> System.exit(0)));

        bootMenuList.printMenu();
        bootMenuList.prompt();
    }

    public static void main(String[] args) {
        new Main();
    }
}