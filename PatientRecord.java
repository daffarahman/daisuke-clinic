public class PatientRecord extends LinkedList<Patient> {
    private int lastId = 0;

    public PatientRecord() {
        super();
    }

    public void addPatient(String name, int age, String address, String phoneNumber) {
        lastId++;
        insertBack(new Patient(lastId, name.strip(), age, address.strip(), phoneNumber.strip()));
    }

    public void removePatientById(int id) {
        if (head != null && head.data.getId() == id) {
            head = head.next;
        } else {
            Node<Patient> current = head;
            while (current.next != null && current.next.data.getId() != id) {
                current = current.next;
            }

            if (current.next != null) {
                current.next = current.next.next;
            }
        }
    }

    public Patient findPatientByName(String name) {
        Node<Patient> current = head;
        while (current != null) {
            if (current.data.getName().equals(name)) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    public void displayAllPatients() {
        Node<Patient> current = head;
        while (current != null) {
            System.out.println(String.format("id:%d,name:%s,age:%d,address:%s,phone_number:%s", current.data.getId(),
                    current.data.getName(), current.data.getAge(), current.data.getAddress(),
                    current.data.getPhoneNumber()));
            current = current.next;
        }
    }
}
