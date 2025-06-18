package daisukeclinic.routes;

import java.time.LocalTime;

import daisukeclinic.components.MenuItem;
import daisukeclinic.components.MenuList;
import daisukeclinic.controller.DoctorList;
import daisukeclinic.controller.PatientRecord;
import daisukeclinic.controller.UserListManager;
import daisukeclinic.model.User;
import daisukeclinic.utils.ConsoleUtility;
import daisukeclinic.view.AppConsole;

public class GreeterRoutes {

    // Login Account
    public static void loginAccountPage() {
        ConsoleUtility.clearScreen();
        ConsoleUtility.printTitle("Login MyDaisuke Account");

        String username = ConsoleUtility.getStringPromptInput("Username: ");

        User u = UserListManager.getInstance().getUser(username);
        if (u == null) {
            System.out.println("User '" + username + "' did not exist!");
            ConsoleUtility.pressAnyKeyToContinue();
            return;
        }

        String password = ConsoleUtility.getPasswordPromptInput("Password: ");
        if (!UserListManager.getInstance().validateUser(username, password)) {
            System.out.println("Wrong password!");
            ConsoleUtility.pressAnyKeyToContinue();
            return;
        }

        if (u.getRole() == User.Role.ROLE_PATIENT)
            AppConsole.getInstance().menuStack.push(AppConsole.getInstance().patientMainMenuList);
        else if (u.getRole() == User.Role.ROLE_DOCTOR) {
            AppConsole.getInstance().menuStack.push(AppConsole.getInstance().doctorMainMenuList);
            DoctorList.getInstance().findDoctorById(u.getRoleId()).login();
        } else if (u.getRole() == User.Role.ROLE_ADMIN)
            AppConsole.getInstance().menuStack.push(AppConsole.getInstance().mainMenuList);

        AppConsole.getInstance().currentUser = u;
    }

    // Register Account
    public static void registerAccountPage() {
        ConsoleUtility.clearScreen();
        ConsoleUtility.printTitle("Register MyDaisuke Account");

        final User.Role[] role = new User.Role[1];
        int roleId;

        MenuList roleMenuList = new MenuList("Select Account Type", 3);
        roleMenuList.addMenuItem(new MenuItem("Patient", () -> {
            role[0] = User.Role.ROLE_PATIENT;
        }));
        roleMenuList.addMenuItem(new MenuItem("Doctor", () -> {
            role[0] = User.Role.ROLE_DOCTOR;
        }));
        roleMenuList.addMenuItem(new MenuItem("Superuser Admin", () -> {
            role[0] = User.Role.ROLE_ADMIN;
        }));

        roleMenuList.printMenu();
        roleMenuList.run(roleMenuList.prompt());

        if (role[0] == null) {
            System.out.println("Please select either of three type, exiting...");
            ConsoleUtility.pressAnyKeyToContinue();
            return;
        }

        String username = ConsoleUtility.getStringPromptInput("Username: ");

        if (UserListManager.getInstance().getUser(username) != null) {
            System.out.println("Username already exist!");
            ConsoleUtility.pressAnyKeyToContinue();
        }

        if (role[0] == User.Role.ROLE_DOCTOR || role[0] == User.Role.ROLE_PATIENT) {
            String fullName = ConsoleUtility.getStringPromptInput("Full Name: ");

            if (role[0] == User.Role.ROLE_DOCTOR) {
                String specialty = ConsoleUtility.getStringPromptInput("Specialty: ");
                LocalTime scheduleStart = ConsoleUtility.getLocalTimePromptInput(
                        "Schedule Starts At: ",
                        LocalTime.of(8, 0), LocalTime.of(18, 0));
                LocalTime scheduleEnd = ConsoleUtility.getLocalTimePromptInput(
                        "Schedule Ends At: ",
                        LocalTime.of(scheduleStart.getHour() + 1, 0), LocalTime.of(19, 0));

                roleId = DoctorList.getInstance().registerDoctor(fullName, specialty, scheduleStart, scheduleEnd);
            } else {
                int age = ConsoleUtility.getIntPromptInput("Age: ");
                String address = ConsoleUtility.getStringPromptInput("Address: ");
                String phoneNumber = ConsoleUtility.getPhoneNumberPromptInput("Phone Number: ");

                roleId = PatientRecord.getInstance().addPatient(fullName, age, address, phoneNumber);
            }
        } else {
            roleId = -1;
        }

        String password = ConsoleUtility.getPasswordPromptInput("Enter Password: ");

        UserListManager.getInstance().addNewUser(username, password, role[0], roleId);

        System.out.println("User is successfuly registered! Now user can login using their MyDaisuke account!");

        ConsoleUtility.pressAnyKeyToContinue();
    }
}
