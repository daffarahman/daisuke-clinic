import java.util.Scanner;

public class PatientRecord {
    public class PatientNode {
        Patient data;
        PatientNode next;
        PatientNode prev;

        public PatientNode(Patient data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }

    public class Linkedlist {
        private PatientNode head;
        private PatientNode tail;
        private int size;
        private int id = 1;

        public void addPatient() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please fullfill this form");
            System.out.println("Name : ");
            String name = scanner.nextLine();
            System.out.println("Age : ");
            int age = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Address");
            String address = scanner.nextLine();
            System.out.println("Phone Number : ");
            String phoneNumber = scanner.nextLine();

            Patient patient = new Patient(id++, name, age, address, phoneNumber);
            PatientNode node = new PatientNode(patient);
            if (head == null) {
                head = tail = node;
            } else {
                tail.next = node;
                node.prev = tail;
                tail = node;
            }
            size++;
            System.out.println("Patient " + name + " successfully added");

        }

        public int getsize() {
            return size;
        }

        public void removePatientById(int id) {
            PatientNode current = head;
            while (current != null) {
                if (current.data.getId() == id) {
                    // remove head
                    if (current == head) {
                        head = current.next;
                        if (head != null) {
                            head.prev = null;
                        } else {
                            tail = null;
                        }
                    } // remove tail
                    else if (current == tail) {
                        tail = current.prev;
                        if (tail != null) {
                            tail.next = null;
                        }
                    } // remove middle
                    else {
                        current.prev.next = current.next;
                        current.next.prev = current.prev;
                    }
                    size--;
                    System.out.println("Patient with ID " + id + " successfully removed");
                    return;
                }
                current = current.next;
            }
            System.out.println("Patient with ID " + id + " not found");
        }

        public boolean findPatientByName(String name) {
            PatientNode current = head;
            while (current != null) {
                if (current.data.getName().equalsIgnoreCase(name)) {
                    System.out.println("Data founded");
                    System.out.println("====================");
                    System.out.println("PATIENT DATA RECORD");
                    System.out.println("ID : " + current.data.getId());
                    System.out.println("NAME : " + current.data.getName());
                    System.out.println("AGE : " + current.data.getAge());
                    System.out.println("ADDRESS : " + current.data.getAddress());
                    System.out.println("PHONE NUMBER : " + current.data.getPhoneNumber());
                    System.out.println("====================");
                    return true;
                }
                current = current.next;
            }
            System.out.println("Patient with name " + name + "not found");
            return false;
        }

        public void displayAllPatients() {
            if (head == null) {
                System.out.println("Patient data is empty");
                return;
            }
            PatientNode current = head;
            System.out.println("Patient List : ");
            while (current != null) {
                System.out.println("\n==============================");
                System.out.println("PATIENT DATA RECORD");
                System.out.println("ID : " + current.data.getId());
                System.out.println("NAME : " + current.data.getName());
                System.out.println("AGE : " + current.data.getAge());
                System.out.println("ADDRESS : " + current.data.getAddress());
                System.out.println("PHONE NUMBER : " + current.data.getPhoneNumber());
                System.out.println("==============================");
                current = current.next;
            }
        }
    }
}
