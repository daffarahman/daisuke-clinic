public class Main {
    private static Doctor[] doctors;

    public static void main(String[] args) {

        doctors = new Doctor[100];
        doctors[0] = new Doctor(0, "Dr. Smith", "Cardiology");
        doctors[1] = new Doctor(1, "Dr. Jones", "Neurology");
        doctors[2] = new Doctor(2, "Dr. Brown", "Pediatrics");
        doctors[3] = new Doctor(3, "Dr. Taylor", "Orthopedics");
        doctors[4] = new Doctor(4, "Dr. Wilson", "Dermatology");
        doctors[5] = new Doctor(5, "Dr. Johnson", "General Practice");
        doctors[6] = new Doctor(6, "Dr. Lee", "Psychiatry");
        doctors[7] = new Doctor(7, "Dr. Garcia", "Oncology");
        doctors[8] = new Doctor(8, "Dr. Martinez", "Radiology");
        doctors[9] = new Doctor(9, "Dr. Anderson", "Gastroenterology");
        doctors[10] = new Doctor(10, "Dr. Thomas", "Endocrinology");
        doctors[11] = new Doctor(11, "Dr. Taylor", "Urology");

        DoctorLoginList doctorLoginList = DoctorLoginList.getInstance();
        doctorLoginList.loginDoctor(doctors[7]);
        doctorLoginList.loginDoctor(doctors[2]);
        doctorLoginList.logoutDoctor(2);

        MenuList menuList = new MenuList("Daisuke Clinic", 13);

        menuList.addMenuItem(new MenuItem("Add New Patient", null));
        menuList.addMenuItem(new MenuItem("Remove Patient by ID", null));
        menuList.addMenuItem(new MenuItem("Search Patient by Name", null));
        menuList.addMenuItem(new MenuItem("Display All Patients", null));
        menuList.addMenuItem(new MenuItem("Doctor Login", null));
        menuList.addMenuItem(new MenuItem("Doctor Logout", null));
        menuList.addMenuItem(new MenuItem("View Last Logged-in Doctor", null));
        menuList.addMenuItem(new MenuItem("Schedule Appointment", null));
        menuList.addMenuItem(new MenuItem("Process Appointment", null));
        menuList.addMenuItem(new MenuItem("Display Upcoming Appointments", null));
        menuList.addMenuItem(new MenuItem("Search Patient by ID (BST)", null));
        menuList.addMenuItem(new MenuItem("Display All Patients (BST Inorder)", null));
        menuList.addMenuItem(new MenuItem("Exit", null));

        menuList.printMenu();
    }
}