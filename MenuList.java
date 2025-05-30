public class MenuList extends LinkedList<MenuItem> {
    private String title;

    public MenuList(String title) {
        this.title = title;
    }

    public void printMenu() {
        
    }

    public void run(int selection) {
        Runnable r = getIndex(selection - 1).getRunnable();
        if (r != null) {
            r.run();
        }
    }
}
