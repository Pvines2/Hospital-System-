# **Hospital Management System**

## **Overview**
The Hospital Management System is a JavaFX-based desktop application that allows users to execute a variety of SQL queries related to hospital operations. Users can view data such as room utilization, patient information, diagnoses, treatments, and employee details. The application connects to a MariaDB database and provides a clean interface for executing these queries with dynamic inputs like date ranges and IDs.

---

## **Features**
- **Room Utilization Queries:**
  - List occupied rooms, unoccupied rooms, and all room details.
- **Patient Information Queries:**
  - List all patients, admitted patients, and patients within specific date ranges.
  - Admission statistics and patients admitted within 30 days of their last discharge.
- **Diagnosis and Treatment Queries:**
  - List diagnoses and treatments by occurrences.
  - Filter diagnoses and treatments for specific doctors or patients.
- **Employee Information Queries:**
  - List all employees, primary doctors with high admissions, and employees involved in all treatments.
- **Dynamic Query Parameters:**
  - Date ranges (using `DatePicker`).
  - IDs for doctors, patients, and treatments.

---

## **Prerequisites**
1. **Database:**
   - A MariaDB database configured with the required tables (e.g., `Room`, `Patient`, `Admission`, `Employee`, etc.).
   - Ensure your database schema matches the SQL queries in the `HospitalManagementSystem` class.
2. **Java Development Kit (JDK):**
   - Java 11 or higher.
3. **JavaFX:**
   - Ensure JavaFX is set up in your development environment.
4. **Build Tool:**
   - IntelliJ IDEA or any IDE supporting JavaFX and Java 11+.
   - Include `mariadb-java-client` in your project's libraries for database connectivity.

---

## **How to Run**

### **Run the Application**
1. Build and run the `HospitalManagementApp` class.

### **Navigate the Interface**
1. Select a query from the dropdown menu.
2. Fill in required inputs (e.g., date ranges or IDs) and click "Execute Query."
3. Results will be displayed in the text area below.

---

## **Interface Walkthrough**
- **Dropdown Menu:**
  - Select a query to execute (e.g., "List All Patients," "List Diagnoses for a Doctor").
- **Dynamic Inputs:**
  - If a query requires specific parameters, input fields for IDs and date ranges will become active.
- **Results Area:**
  - Query results or error messages are displayed in this section.
- **Status Label:**
  - Displays feedback for successful or failed query execution.
- **Clear Results Button:**
  - Resets the results and status label.

---

## **Project Structure & Key Classes**

### **HospitalManagementSystem**
Contains all SQL queries and database connection logic.

**Example methods:**
- `listOccupiedRooms()`
- `listPatientsAdmittedInDateRange(String startDate, String endDate)`
- `listDiagnosesForDoctor(int doctorId)`

### **HospitalManagementController**
Manages user input and interface interactions.

**Example methods:**
- `validateInputs(String selectedQuery)`
- `executeSelectedQuery()`
- `handleQuerySelection()`

---

## **Known Issues & Things To Watch For**
- **Database Connection Failures:**
  - Ensure the MariaDB server is running and the credentials are correct.
- **Empty Inputs for Queries:**
  - Use the validation system to ensure required parameters are filled.

---

## **Contributors**
- **Your Name:** Parker Vines

---


## **License**
This project is licensed under the MIT License. See the LICENSE file for details.
