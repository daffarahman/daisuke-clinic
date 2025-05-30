import java.util.Scanner;

public class MenuList {
    private Scanner scanner;

    private String title;
    private MenuItem[] menuItems;
    private int menuCount;

    public MenuList(String title, int size) {
        scanner = new Scanner(System.in);

        this.title = title;
        this.menuItems = new MenuItem[size];
        this.menuCount = 0;
        ;
    }

    public void addMenuItem(MenuItem menuItem) {
        menuItems[menuCount++] = menuItem;
    }

    public void printMenu() {
        int menuWidth = getMenuWidth();

        System.out.print("╔");
        Utility.printChars('═', menuWidth);
        System.out.println("╗");

        if (title != null && !title.isEmpty()) {
            int padding = (menuWidth - title.length()) / 2;
            System.out.print("║");
            Utility.printChars(' ', padding);
            System.out.print(title);
            Utility.printChars(' ', menuWidth - title.length() - padding);
            System.out.println("║");

            System.out.print("╠");
            Utility.printChars('═', menuWidth);
            System.out.println("╣");
        }

        for (int i = 0; i < menuItems.length; i++) {
            if (menuItems[i] != null) {
                String menuText = String.format("%2d. %-" + (menuWidth - 6) + "s", i + 1, menuItems[i].getText());
                System.out.print("║ ");
                System.out.print(menuText);
                System.out.println(" ║");
            }
        }

        System.out.print("╚");
        Utility.printChars('═', menuWidth);
        System.out.println("╝");
    }

    public int prompt() {
        int selection = 0;
        System.out.print("Selection -> ");
        try {
            selection = scanner.nextInt();
        } catch (Exception e) {
        }
        return selection;
    }

    public void run(int selection) {
        selection--;
        if (selection >= 0 && selection < menuItems.length && menuItems[selection] != null) {
            menuItems[selection].run();
        }
    }

    private int getMenuWidth() {
        int width = title != null ? title.length() : 0;
        for (int i = 0; i < menuItems.length; i++) {
            if (menuItems[i].getText().length() > width) {
                width = menuItems[i].getText().length();
            }
        }
        return width + 8;
    }
}
