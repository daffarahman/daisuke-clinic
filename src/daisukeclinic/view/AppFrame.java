package daisukeclinic.view;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class AppFrame extends JFrame {
    private JPanel cardPanel;
    private CardLayout cardLayout;

    public AppFrame() {
        setTitle("Daisuke Clinic Executive");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        add(cardPanel);
        setVisible(true);
    }
}