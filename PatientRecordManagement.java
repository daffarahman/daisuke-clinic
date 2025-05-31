import java.util.Scanner;

class Patient implements Comparable <Patient>{
    int ID;
    String name;
    int age;
    Address address;
    String phoneNumber;

    public Patient (int ID, String name, int age, Address address, String phoneNumber){
        this.ID = ID;
        this.name = name;
        this.age = age;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public int compareTo(Patient other){
        return Integer.compare(this.ID, other.ID);
    }

    @Override
    public String toString() {
        return "[Patient "  + ID + "]  Name: " + name + ", age: " + age + ", address:" + address + ", phoneNumber: " + phoneNumber ;
    }
}

class Address{
    String subdistrict;
    String city;
    String province;

    
    public Address(String subdistrict, String city, String province) {
        this.subdistrict = subdistrict;
        this.city = city;
        this.province = province;
    }

    @Override
    public String toString() {
        return subdistrict + ", " + city + ", " + province;
    }
}

public class PatientRecordManagement {
    static BST<Patient> Pasien = new BST<>();

    public static void main(String[] args) {
        //test inorder display
        Pasien.insert(new Node<>(new Patient(105, "Alice", 30, new Address("A", "CityA", "ProvA"), "08123456789")));
        Pasien.insert(new Node<>(new Patient(103, "Bob", 25, new Address("B", "CityB", "ProvB"), "08234567890")));
        Pasien.insert(new Node<>(new Patient(101, "Joko", 49, new Address("F", "CityF", "ProvF"), "08345678904")));
        Pasien.insert(new Node<>(new Patient(102, "Charlie", 40, new Address("C", "CityC", "ProvC"), "08345678901")));
        Pasien.insert(new Node<>(new Patient(106, "Wowok", 40, new Address("E", "CityE", "ProvE"), "08345678969")));
        Pasien.insert(new Node<>(new Patient(104, "Dapa", 69, new Address("D", "CityD", "ProvD"), "08345678666")));

        inOrderDisplay();
    }

    public static void insertPatient(){//Fungsi ini digabung aja dap sama yang addPatient yang pake LinkedList
        Scanner menu1Scanner = new Scanner(System.in);
        System.out.println("Insert Patient");

        System.out.print("Patient ID  : ");
        int ID = menu1Scanner.nextInt();
        menu1Scanner.nextLine();

        System.out.print("Patient name: ");
        String name = menu1Scanner.nextLine();

        System.out.print("Patient age: ");
        int age = menu1Scanner.nextInt();
        menu1Scanner.nextLine();

        System.out.println("Patient addres");
        System.out.print("Subdistrict: ");
        String subdistrict = menu1Scanner.nextLine();

        System.out.print("City       : ");
        String city = menu1Scanner.nextLine();

        System.out.print("Province: ");
        String province = menu1Scanner.nextLine();

        System.out.print("Patient phone number: ");
        String phoneNumber = menu1Scanner.nextLine();

        Pasien.insert(new Node<>(new Patient(ID, name, age, new Address(subdistrict, city, province), phoneNumber)));

        
        System.out.println("Patient Successfully added");

        menu1Scanner.close();

    }

    public static void searchPatient(){
        Scanner menu2Scanner = new Scanner(System.in);

        System.out.println("Search patient by ID");
        System.out.print("Paient ID: ");
        int ID = menu2Scanner.nextInt();

        Patient test = new Patient(ID, "", 0, new Address("", "", ""), "");
        // Node<Patient> result = Pasien.search(test);

        // if (result != null) {
        //     Patient p = result.data;
        //     System.out.println("Patient Found:");
        //     System.out.println("ID      : " + p.ID);
        //     System.out.println("Name    : " + p.name);
        //     System.out.println("Age     : " + p.age);
        //     System.out.println("Phone   : " + p.phoneNumber);
        //     System.out.println("Address : " + p.address.subdistrict + ", " + p.address.city + ", " + p.address.province);
        // } else {
        //     System.out.println("Patient not found");
        // }

        boolean found = Pasien.search(test);
        if(found){
            System.out.println("Patient with ID " + ID + " found");
        }
        else{
            System.out.println("Patient with ID " + ID + " not found");
        }
        menu2Scanner.close();
    }

    public static void inOrderDisplay(){
        Pasien.display();
    }
}
