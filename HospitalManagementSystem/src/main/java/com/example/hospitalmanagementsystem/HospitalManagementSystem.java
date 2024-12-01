package com.example.hospitalmanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class HospitalManagementSystem {
    private static final String DB_URL = "jdbc:mariadb://sysmysql8.auburn.edu:3306/pdv0006db";
    private static final String USER = "pdv0006";
    private static final String PASSWORD = "Parker1864!!";


    public static Connection getConnection() throws Exception {
        try {
            return DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (Exception e) {
            System.err.println("Database connection failed: " + e.getMessage());
            throw new Exception("Unable to connect to database: " + e.getMessage(), e);
        }
    }


    public static String listOccupiedRooms() {
        StringBuilder result = new StringBuilder();
        String query = "SELECT Room.room_number, Patient.name, Admission.admission_date " +
                "FROM Room " +
                "JOIN Admission ON Room.room_number = Admission.room_number " +
                "JOIN Patient ON Admission.patient_id = Patient.patient_id " +
                "WHERE Room.status = TRUE";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            result.append("Occupied Rooms:\n");
            while (rs.next()) {
                result.append(String.format("Room %d: %s (Admitted on %s)\n",
                        rs.getInt("room_number"),
                        rs.getString("name"),
                        rs.getDate("admission_date")));
            }
        } catch (Exception e) {
            result.append("Error: ").append(e.getMessage());
        }
        return result.toString();
    }


    public static String listUnoccupiedRooms() {
        StringBuilder result = new StringBuilder();
        String query = "SELECT room_number FROM Room WHERE status = FALSE";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            result.append("Unoccupied Rooms:\n");
            while (rs.next()) {
                result.append(String.format("Room %d\n", rs.getInt("room_number")));
            }
        } catch (Exception e) {
            result.append("Error: ").append(e.getMessage());
        }
        return result.toString();
    }


    public static String listAllRoomsWithDetails() {
        StringBuilder result = new StringBuilder();
        String query = "SELECT Room.room_number, Patient.name, Admission.admission_date " +
                "FROM Room " +
                "LEFT JOIN Admission ON Room.room_number = Admission.room_number " +
                "LEFT JOIN Patient ON Admission.patient_id = Patient.patient_id";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            result.append("All Rooms:\n");
            while (rs.next()) {
                result.append(String.format("Room %d: %s (Admitted on %s)\n",
                        rs.getInt("room_number"),
                        rs.getString("name"),
                        rs.getDate("admission_date")));
            }
        } catch (Exception e) {
            result.append("Error: ").append(e.getMessage());
        }
        return result.toString();
    }


    public static String listAllPatients() {
        StringBuilder result = new StringBuilder();
        String query = "SELECT * FROM Patient";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            result.append("Patients:\n");
            while (rs.next()) {
                result.append(String.format("%d: %s, %s, %s, %s\n",
                        rs.getInt("patient_id"),
                        rs.getString("name"),
                        rs.getString("record_id"),
                        rs.getString("emergency_contact"),
                        rs.getString("insurance_policy")));
            }
        } catch (Exception e) {
            result.append("Error: ").append(e.getMessage());
        }
        return result.toString();
    }

    public static String listAdmittedPatients() {
        StringBuilder result = new StringBuilder();
        String query = "SELECT Patient.patient_id, Patient.name " +
                "FROM Admission " +
                "JOIN Patient ON Admission.patient_id = Patient.patient_id " +
                "WHERE Admission.discharge_date IS NULL";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            result.append("Admitted Patients:\n");
            while (rs.next()) {
                result.append(String.format("Patient ID: %d, Name: %s\n",
                        rs.getInt("patient_id"),
                        rs.getString("name")));
            }
        } catch (Exception e) {
            result.append("Error: ").append(e.getMessage());
        }
        return result.toString();
    }


    public static String listCurrentlyAdmittedPatients() {
        StringBuilder result = new StringBuilder();
        String query = "SELECT Patient.patient_id, Patient.name " +
                "FROM Admission " +
                "JOIN Patient ON Admission.patient_id = Patient.patient_id " +
                "WHERE Admission.discharge_date IS NULL";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            result.append("Currently Admitted Patients:\n");
            while (rs.next()) {
                result.append(String.format("Patient ID: %d, Name: %s\n",
                        rs.getInt("patient_id"),
                        rs.getString("name")));
            }
        } catch (Exception e) {
            result.append("Error: ").append(e.getMessage());
        }
        return result.toString();
    }


    public static String listPatientsDischargedInDateRange(String startDate, String endDate) {
        StringBuilder result = new StringBuilder();
        String query = String.format(
                "SELECT Patient.patient_id, Patient.name " +
                        "FROM Admission " +
                        "JOIN Patient ON Admission.patient_id = Patient.patient_id " +
                        "WHERE Admission.discharge_date BETWEEN '%s' AND '%s'",
                startDate, endDate);

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            result.append("Discharged Patients:\n");
            while (rs.next()) {
                result.append(String.format("Patient ID: %d, Name: %s\n",
                        rs.getInt("patient_id"),
                        rs.getString("name")));
            }
        } catch (Exception e) {
            result.append("Error: ").append(e.getMessage());
        }
        return result.toString();
    }


    public static String listPatientsAdmittedInDateRange(String startDate, String endDate) {
        StringBuilder result = new StringBuilder();
        String query = String.format(
                "SELECT Patient.patient_id, Patient.name " +
                        "FROM Admission " +
                        "JOIN Patient ON Admission.patient_id = Patient.patient_id " +
                        "WHERE Admission.admission_date BETWEEN '%s' AND '%s'",
                startDate, endDate);

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            result.append("Patients Admitted in Date Range:\n");
            while (rs.next()) {
                result.append(String.format("Patient ID: %d, Name: %s\n",
                        rs.getInt("patient_id"),
                        rs.getString("name")));
            }
        } catch (Exception e) {
            result.append("Error: ").append(e.getMessage());
        }
        return result.toString();
    }



    public static String listDiagnosesByOccurrences() {
        StringBuilder result = new StringBuilder();
        String query = "SELECT Admission.initial_diagnosis, COUNT(*) AS occurrences " +
                "FROM Admission " +
                "GROUP BY Admission.initial_diagnosis " +
                "ORDER BY occurrences DESC";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            result.append("Diagnoses by Occurrences:\n");
            while (rs.next()) {
                result.append(String.format("Diagnosis: %s, Occurrences: %d\n",
                        rs.getString("initial_diagnosis"),
                        rs.getInt("occurrences")));
            }
        } catch (Exception e) {
            result.append("Error: ").append(e.getMessage());
        }
        return result.toString();
    }


    public static String listAdmissionsForPatient(int patientId, String patientName) {
        StringBuilder result = new StringBuilder();
        String query = String.format(
                "SELECT Admission.admission_id, Admission.admission_date, Admission.initial_diagnosis " +
                        "FROM Admission " +
                        "JOIN Patient ON Admission.patient_id = Patient.patient_id " +
                        "WHERE Patient.patient_id = %d OR Patient.name = '%s'",
                patientId, patientName);

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            result.append("Admissions for Patient:\n");
            while (rs.next()) {
                result.append(String.format("Admission ID: %d, Date: %s, Diagnosis: %s\n",
                        rs.getInt("admission_id"),
                        rs.getDate("admission_date"),
                        rs.getString("initial_diagnosis")));
            }
        } catch (Exception e) {
            result.append("Error: ").append(e.getMessage());
        }
        return result.toString();
    }


    public static String listTreatmentsForPatient(int patientId, String patientName) {
        StringBuilder result = new StringBuilder();
        String query = String.format(
                "SELECT Admission.admission_date, Treatment.description, Treatment.timestamp_ordered " +
                        "FROM Admission " +
                        "JOIN Patient ON Admission.patient_id = Patient.patient_id " +
                        "JOIN Treatment ON Admission.admission_id = Treatment.admission_id " +
                        "WHERE Patient.patient_id = %d OR Patient.name = '%s' " +
                        "ORDER BY Admission.admission_date DESC, Treatment.timestamp_ordered ASC",
                patientId, patientName);

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            result.append("Treatments for Patient:\n");
            while (rs.next()) {
                result.append(String.format("Date: %s, Treatment: %s, Ordered At: %s\n",
                        rs.getDate("admission_date"),
                        rs.getString("description"),
                        rs.getTimestamp("timestamp_ordered")));
            }
        } catch (Exception e) {
            result.append("Error: ").append(e.getMessage());
        }
        return result.toString();
    }


    public static String listPatientsAdmittedWithin30DaysOfDischarge() {
        StringBuilder result = new StringBuilder();
        String query = "SELECT DISTINCT Patient.patient_id, Patient.name, a1.initial_diagnosis, e.name AS admitting_doctor " +
                "FROM Admission AS a1 " +
                "JOIN Admission AS a2 ON a1.patient_id = a2.patient_id " +
                "JOIN Patient ON a1.patient_id = Patient.patient_id " +
                "JOIN Employee AS e ON a1.primary_doctor_id = e.employee_id " +
                "WHERE a1.admission_date > a2.discharge_date " +
                "AND DATEDIFF(a1.admission_date, a2.discharge_date) <= 30";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            result.append("Patients Admitted Within 30 Days of Last Discharge:\n");
            while (rs.next()) {
                result.append(String.format("Patient ID: %d, Name: %s, Diagnosis: %s, Doctor: %s\n",
                        rs.getInt("patient_id"),
                        rs.getString("name"),
                        rs.getString("initial_diagnosis"),
                        rs.getString("admitting_doctor")));
            }
        } catch (Exception e) {
            result.append("Error: ").append(e.getMessage());
        }
        return result.toString();
    }


    public static String listAdmissionStatisticsPerPatient() {
        StringBuilder result = new StringBuilder();
        String query = "SELECT Patient.patient_id, Patient.name, " +
                "COUNT(Admission.admission_id) AS total_admissions, " +
                "AVG(DATEDIFF(Admission.discharge_date, Admission.admission_date)) AS avg_admission_duration, " +
                "MAX(DATEDIFF(a1.admission_date, a2.discharge_date)) AS longest_span_between, " +
                "MIN(DATEDIFF(a1.admission_date, a2.discharge_date)) AS shortest_span_between, " +
                "AVG(DATEDIFF(a1.admission_date, a2.discharge_date)) AS avg_span_between " +
                "FROM Patient " +
                "JOIN Admission ON Patient.patient_id = Admission.patient_id " +
                "LEFT JOIN Admission AS a1 ON Patient.patient_id = a1.patient_id " +
                "LEFT JOIN Admission AS a2 ON Patient.patient_id = a2.patient_id " +
                "GROUP BY Patient.patient_id";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            result.append("Admission Statistics per Patient:\n");
            while (rs.next()) {
                result.append(String.format("Patient ID: %d, Name: %s, Total Admissions: %d, Avg Duration: %.2f, " +
                                "Longest Span: %.2f, Shortest Span: %.2f, Avg Span: %.2f\n",
                        rs.getInt("patient_id"),
                        rs.getString("name"),
                        rs.getInt("total_admissions"),
                        rs.getDouble("avg_admission_duration"),
                        rs.getDouble("longest_span_between"),
                        rs.getDouble("shortest_span_between"),
                        rs.getDouble("avg_span_between")));
            }
        } catch (Exception e) {
            result.append("Error: ").append(e.getMessage());
        }
        return result.toString();
    }


    public static String listDiagnosesForHospitalPatients() {
        StringBuilder result = new StringBuilder();
        String query = "SELECT Admission.initial_diagnosis, COUNT(*) AS occurrences " +
                "FROM Admission " +
                "GROUP BY Admission.initial_diagnosis " +
                "ORDER BY occurrences DESC";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            result.append("Diagnoses for Hospital Patients by Occurrences:\n");
            while (rs.next()) {
                result.append(String.format("Diagnosis: %s, Occurrences: %d\n",
                        rs.getString("initial_diagnosis"),
                        rs.getInt("occurrences")));
            }
        } catch (Exception e) {
            result.append("Error: ").append(e.getMessage());
        }
        return result.toString();
    }


    public static String listTreatmentsByOccurrences() {
        StringBuilder result = new StringBuilder();
        String query = "SELECT Treatment.description, COUNT(*) AS occurrences " +
                "FROM Treatment " +
                "GROUP BY Treatment.description " +
                "ORDER BY occurrences DESC";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            result.append("Treatments by Occurrences:\n");
            while (rs.next()) {
                result.append(String.format("Treatment: %s, Occurrences: %d\n",
                        rs.getString("description"),
                        rs.getInt("occurrences")));
            }
        } catch (Exception e) {
            result.append("Error: ").append(e.getMessage());
        }
        return result.toString();
    }


    public static String listDiagnosesWithHighestAdmissions() {
        StringBuilder result = new StringBuilder();
        String query = "SELECT Admission.initial_diagnosis, COUNT(*) AS occurrences " +
                "FROM Admission " +
                "GROUP BY Admission.initial_diagnosis " +
                "ORDER BY occurrences ASC";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            result.append("Diagnoses with Highest Admissions (Ascending Correlation):\n");
            while (rs.next()) {
                result.append(String.format("Diagnosis: %s, Occurrences: %d\n",
                        rs.getString("initial_diagnosis"),
                        rs.getInt("occurrences")));
            }
        } catch (Exception e) {
            result.append("Error: ").append(e.getMessage());
        }
        return result.toString();
    }


    public static String listPatientAndDoctorForTreatment(int treatmentId) {
        StringBuilder result = new StringBuilder();
        String query = String.format(
                "SELECT Patient.name AS patient_name, Employee.name AS doctor_name " +
                        "FROM Treatment " +
                        "JOIN Admission ON Treatment.admission_id = Admission.admission_id " +
                        "JOIN Patient ON Admission.patient_id = Patient.patient_id " +
                        "JOIN Employee ON Treatment.ordered_by_doctor_id = Employee.employee_id " +
                        "WHERE Treatment.treatment_id = %d", treatmentId);

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            result.append("Patient and Doctor for Treatment:\n");
            while (rs.next()) {
                result.append(String.format("Patient: %s, Doctor: %s\n",
                        rs.getString("patient_name"),
                        rs.getString("doctor_name")));
            }
        } catch (Exception e) {
            result.append("Error: ").append(e.getMessage());
        }
        return result.toString();
    }


    public static String listAllWorkers() {
        StringBuilder result = new StringBuilder();
        String query = "SELECT name, role FROM Employee ORDER BY name ASC";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            result.append("All Workers:\n");
            while (rs.next()) {
                result.append(String.format("Name: %s, Role: %s\n",
                        rs.getString("name"),
                        rs.getString("role")));
            }
        } catch (Exception e) {
            result.append("Error: ").append(e.getMessage());
        }
        return result.toString();
    }


    public static String listPrimaryDoctorsWithHighAdmissions() {
        StringBuilder result = new StringBuilder();
        String query = "SELECT Employee.name AS doctor_name, COUNT(Admission.admission_id) AS admission_count " +
                "FROM Admission " +
                "JOIN Employee ON Admission.primary_doctor_id = Employee.employee_id " +
                "GROUP BY Employee.name " +
                "HAVING admission_count >= 4";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            result.append("Primary Doctors with High Admissions:\n");
            while (rs.next()) {
                result.append(String.format("Doctor: %s, Admission Count: %d\n",
                        rs.getString("doctor_name"),
                        rs.getInt("admission_count")));
            }
        } catch (Exception e) {
            result.append("Error: ").append(e.getMessage());
        }
        return result.toString();
    }


    public static String listDiagnosesForDoctor(int doctorId) {
        StringBuilder result = new StringBuilder();
        String query = String.format(
                "SELECT Admission.initial_diagnosis, COUNT(*) AS occurrences " +
                        "FROM Admission " +
                        "JOIN Employee ON Admission.primary_doctor_id = Employee.employee_id " +
                        "WHERE Employee.employee_id = %d " +
                        "GROUP BY Admission.initial_diagnosis " +
                        "ORDER BY occurrences DESC", doctorId);

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            result.append(String.format("Diagnoses for Doctor ID %d:\n", doctorId));
            while (rs.next()) {
                result.append(String.format("Diagnosis: %s, Occurrences: %d\n",
                        rs.getString("initial_diagnosis"),
                        rs.getInt("occurrences")));
            }
        } catch (Exception e) {
            result.append("Error: ").append(e.getMessage());
        }
        return result.toString();
    }


    public static String listTreatmentsOrderedByDoctor(int doctorId) {
        StringBuilder result = new StringBuilder();
        String query = String.format(
                "SELECT Treatment.description, COUNT(*) AS occurrences " +
                        "FROM Treatment " +
                        "JOIN Employee ON Treatment.ordered_by_doctor_id = Employee.employee_id " +
                        "WHERE Employee.employee_id = %d " +
                        "GROUP BY Treatment.description " +
                        "ORDER BY occurrences DESC", doctorId);

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            result.append(String.format("Treatments Ordered by Doctor ID %d:\n", doctorId));
            while (rs.next()) {
                result.append(String.format("Treatment: %s, Occurrences: %d\n",
                        rs.getString("description"),
                        rs.getInt("occurrences")));
            }
        } catch (Exception e) {
            result.append("Error: ").append(e.getMessage());
        }
        return result.toString();
    }


    public static String listEmployeesInvolvedInAllPatientTreatments() {
        StringBuilder result = new StringBuilder();
        String query = "SELECT Employee.employee_id, Employee.name " +
                "FROM Employee " +
                "JOIN TreatmentAdministration ON Employee.employee_id = TreatmentAdministration.administered_by_employee_id " +
                "GROUP BY Employee.employee_id " +
                "HAVING COUNT(DISTINCT TreatmentAdministration.patient_id) = " +
                "(SELECT COUNT(DISTINCT patient_id) FROM Admission)";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            result.append("Employees Involved in Every Patient's Treatment:\n");
            while (rs.next()) {
                result.append(String.format("Employee ID: %d, Name: %s\n",
                        rs.getInt("employee_id"),
                        rs.getString("name")));
            }
        } catch (Exception e) {
            result.append("Error: ").append(e.getMessage());
        }
        return result.toString();
    }


}
