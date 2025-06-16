# DaisukeClinic
Small Clinic, BIG Impact.

![alt text](screenshots/hero.png)

## About This App
Daisuke Clinic is a **console based data management application** for a small clinic built using Java.

## Features



## Our Team

| **Name**                       | **NIM**  | **Task**                                             |
| ------------------------------ | -------- | ---------------------------------------------------- |
| Muhammad Daffa Rahman          | L0124062 | Console UI, Navigation, and Data Structure Interface |
| Naufal Ahmad Fakhriza          | L0124068 | AppointmentQueue                                     |
| Phyrurizqi Altiano Firdauzan   | L0124069 | DoctorLoginList and SearchablePatientTree            |
| Majeeda Athaya Nashwanaira Ali | L0124104 | PatientRecord and MedicalRecord                      |
| M. Faza Zulfan Balya           | L0124107 |                                                      |

## How To Run?

### Prerequisites
* Java Development Kit (JDK) Version 17 or Higher

#### Unix based OS (macOS, Linux)
1. **Navigate to the Project Directory**:
   ```bash
   cd /path/to/DaisukeClinic
   ```
2. **Compile the project**
   Use the following commands to compile all `.java` files into the `out` directory:
   ```bash
    find src -name "*.java" > sources.txt
    javac -d out @sources.txt
    rm sources.txt
   ```
3. **Run the Project**
   Run the `Main` class from the compiled files in `out` directory:
    ```bash
    java -cp out daisukeclinic.Main
    ```

#### Windows
1. **Navigate to the Project Directory**:
   ```batch
   cd \path\to\DaisukeClinic
   ```
2. **Compile the project**
   Use the following commands to compile all `.java` files into the `out` directory:
   ```batch
    dir /s /b src\*.java > sources.txt
    javac -d out @sources.txt
    del sources.txt
   ```
3. **Run the Project**
   Run the `Main` class from the compiled files in `out` directory:
    ```batch
    java -cp out daisukeclinic.Main
    ```

## Flow Of The Program

## Feature Checklist Proof