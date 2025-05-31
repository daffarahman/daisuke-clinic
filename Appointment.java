public class Appointment {
    private Queue<Patient> patientQueue = new Queue<>();
    private LinkedList<Patient> patientList = new LinkedList<>();

    public void addPatient(int id, String name, int age, String address, String phoneNumber) {
        Patient newPatient = new Patient(id, name, age, address, phoneNumber);
        patientQueue.enqueue(newPatient);
        patientList.insertBack(newPatient);
    }

    public void showQueue() {
        System.out.println("Patient Queue");
        patientQueue.display();
    }

    public void showAllPatients() {
        System.out.println("List all patient");
        patientList.display();
    }

    public void callNextPatient() {
        Patient next = patientQueue.dequeue();
        if (next != null) {
            System.out.println("Patient now: " + next);
        } else {
            System.out.println("Queue is empty.");
        }
    }
}
