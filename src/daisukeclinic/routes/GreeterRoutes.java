package daisukeclinic.routes;

import java.time.LocalTime;

import daisukeclinic.components.MenuItem;
import daisukeclinic.components.MenuList;
import daisukeclinic.controller.DoctorList;
import daisukeclinic.controller.DoctorLoginList;
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
            DoctorLoginList.getInstance().loginDoctor(DoctorList.getInstance().findDoctorById(u.getRoleId()));
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
        roleMenuList.addMenuItem(new MenuItem("Administrator", () -> {
            role[0] = User.Role.ROLE_ADMIN;
        }));

        roleMenuList.printMenu();
        roleMenuList.run(roleMenuList.prompt());

        if (role[0] == null) {
            System.out.println("Please select either of three type, exiting...");
            ConsoleUtility.pressAnyKeyToContinue();
            return;
        }

        ConsoleUtility.printChars('-', ConsoleUtility.getConsoleWidth());

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
                DoctorList.getInstance().findDoctorById(roleId).setIsConnectedAccount(true);
            } else {
                int age = ConsoleUtility.getIntPromptInput("Age: ");
                String address = ConsoleUtility.getStringPromptInput("Address: ");
                String phoneNumber = ConsoleUtility.getPhoneNumberPromptInput("Phone Number: ");

                roleId = PatientRecord.getInstance().addPatient(fullName, age, address, phoneNumber);
                PatientRecord.getInstance().findPatientById(roleId).setIsConnectedAccount(true);
            }
        } else {
            roleId = -1;
        }

        ConsoleUtility.printChars('-', ConsoleUtility.getConsoleWidth());

        if (role[0] != User.Role.ROLE_ADMIN) {
            System.out.println("Note: username can be different from real name");
        }

        String username = ConsoleUtility.getStringPromptInput("New username: ");

        if (UserListManager.getInstance().getUser(username) != null) {
            System.out.println("Username already exist! exiting...");
            ConsoleUtility.pressAnyKeyToContinue();
            return;
        }

        String password = ConsoleUtility.getPasswordPromptInput("New Password: ");

        UserListManager.getInstance().addNewUser(username, password, role[0], roleId);

        System.out.println("User is successfuly registered! Now user can login using their MyDaisuke account!");

        ConsoleUtility.pressAnyKeyToContinue();
    }
}
