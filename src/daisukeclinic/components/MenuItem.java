package daisukeclinic.components;

public class MenuItem {
    private String text;
    private Runnable runnable;

    public MenuItem(String text, Runnable runnable) {
        this.text = text;
        this.runnable = runnable;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    public void run() {
        if (runnable != null) {
            runnable.run();
        }
    }
}
