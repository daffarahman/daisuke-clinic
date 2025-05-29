import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);

        PatientRecord patientRecord = new PatientRecord();
        patientRecord.addPatient("John Doe", 30, "123 Elm St", "555-1234");
        patientRecord.addPatient("Jane Smith", 25, "456 Oak St", "555-5678");
        patientRecord.displayAllPatients();
        patientRecord.removePatientById(2);
        System.out.println("After removing patient with ID 2:");
        patientRecord.displayAllPatients();
        patientRecord.addPatient("John Doe", 30, "123 Elm St", "555-1234");
        System.out.println("After");
        patientRecord.displayAllPatients();
    }

    public static void instanceNewGUI() {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }
}