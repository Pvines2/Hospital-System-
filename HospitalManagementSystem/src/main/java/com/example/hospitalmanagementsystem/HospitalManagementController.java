package com.example.hospitalmanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class HospitalManagementController {

    @FXML
    private Label connectionStatus;
    @FXML
    private TextArea resultArea;
    @FXML
    private Label statusLabel;

    @FXML
    private ComboBox<String> queryComboBox;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private TextField doctorIdField;
    @FXML
    private TextField patientIdField;
    @FXML
    private TextField treatmentIdField;

    @FXML
    public void initialize() {
        // Populate dropdown with query options
        queryComboBox.getItems().addAll(
                "List Occupied Rooms",
                "List Unoccupied Rooms",
                "List All Rooms",
                "List All Patients",
                "List Admitted Patients",
                "List Diagnoses by Occurrences",
                "List Treatments by Occurrences",
                "List Diagnoses for a Doctor",
                "List Treatments for a Doctor",
                "List Employees Involved in All Treatments",
                "List Patients Admitted Within 30 Days of Last Discharge",
                "List Admission Statistics per Patient",
                "List Diagnoses with Highest Admissions",
                "List Patient and Doctor for a Treatment",
                "List All Workers",
                "List Primary Doctors with High Admissions",
                "List Patients Admitted in a Date Range",
                "List Patients Discharged in a Date Range"
        );

        // Test database connection
        try {
            if (HospitalManagementSystem.getConnection() != null) {
                connectionStatus.setText("Connected to Database");
                connectionStatus.setStyle("-fx-text-fill: green; -fx-font-size: 14;");
            }
        } catch (Exception e) {
            connectionStatus.setText("Failed to Connect to Database");
            connectionStatus.setStyle("-fx-text-fill: red; -fx-font-size: 14;");
        }
    }

    @FXML
    private boolean validateInputs(String selectedQuery) {
        switch (selectedQuery) {

            case "List Patients Discharged in a Date Range":
            case "List Patients Admitted in a Date Range":
                if (startDatePicker.getValue() == null || endDatePicker.getValue() == null) {
                    statusLabel.setText("Start Date and End Date are required.");
                    statusLabel.setStyle("-fx-text-fill: red;");
                    return false;
                }
                break;
            case "List Diagnoses for a Doctor":
            case "List Treatments for a Doctor":
                if (doctorIdField.getText().isEmpty()) {
                    statusLabel.setText("Doctor ID is required.");
                    statusLabel.setStyle("-fx-text-fill: red;");
                    return false;
                }
                break;
            case "List All Admissions for a Patient":
            case "List Treatments for a Patient":
                if (patientIdField.getText().isEmpty()) {
                    statusLabel.setText("Patient ID is required.");
                    statusLabel.setStyle("-fx-text-fill: red;");
                    return false;
                }
                break;
            case "List Patient and Doctor for a Treatment":
                if (treatmentIdField.getText().isEmpty()) {
                    statusLabel.setText("Treatment ID is required.");
                    statusLabel.setStyle("-fx-text-fill: red;");
                    return false;
                }
                break;
        }
        return true;
    }


    @FXML
    private void executeSelectedQuery() {
        String selectedQuery = queryComboBox.getValue();
        try {
            switch (selectedQuery) {
                case "List Patients Admitted in a Date Range":
                    String startDateAdmitted = startDatePicker.getValue().toString();
                    String endDateAdmitted = endDatePicker.getValue().toString();
                    resultArea.setText(HospitalManagementSystem.listPatientsAdmittedInDateRange(startDateAdmitted, endDateAdmitted));
                    break;
                case "List Patients Discharged in a Date Range":
                    String startDateDischarged = startDatePicker.getValue().toString();
                    String endDateDischarged = endDatePicker.getValue().toString();
                    resultArea.setText(HospitalManagementSystem.listPatientsDischargedInDateRange(startDateDischarged, endDateDischarged));
                    break;
                case "List Occupied Rooms":
                    resultArea.setText(HospitalManagementSystem.listOccupiedRooms());
                    break;
                case "List Unoccupied Rooms":
                    resultArea.setText(HospitalManagementSystem.listUnoccupiedRooms());
                    break;
                case "List All Rooms":
                    resultArea.setText(HospitalManagementSystem.listAllRoomsWithDetails());
                    break;
                case "List All Patients":
                    resultArea.setText(HospitalManagementSystem.listAllPatients());
                    break;
                case "List Admitted Patients":
                    resultArea.setText(HospitalManagementSystem.listAdmittedPatients());
                    break;
                case "List Diagnoses by Occurrences":
                    resultArea.setText(HospitalManagementSystem.listDiagnosesByOccurrences());
                    break;
                case "List Treatments by Occurrences":
                    resultArea.setText(HospitalManagementSystem.listTreatmentsByOccurrences());
                    break;
                case "List Diagnoses for a Doctor":
                    int doctorId = Integer.parseInt(doctorIdField.getText());
                    resultArea.setText(HospitalManagementSystem.listDiagnosesForDoctor(doctorId));
                    break;
                case "List Treatments for a Doctor":
                    int treatmentDoctorId = Integer.parseInt(doctorIdField.getText());
                    resultArea.setText(HospitalManagementSystem.listTreatmentsOrderedByDoctor(treatmentDoctorId));
                    break;
                case "List Employees Involved in All Treatments":
                    resultArea.setText(HospitalManagementSystem.listEmployeesInvolvedInAllPatientTreatments());
                    break;
                case "List Patients Admitted Within 30 Days of Last Discharge":
                    resultArea.setText(HospitalManagementSystem.listPatientsAdmittedWithin30DaysOfDischarge());
                    break;
                case "List Admission Statistics per Patient":
                    resultArea.setText(HospitalManagementSystem.listAdmissionStatisticsPerPatient());
                    break;
                case "List Diagnoses with Highest Admissions":
                    resultArea.setText(HospitalManagementSystem.listDiagnosesWithHighestAdmissions());
                    break;
                case "List Patient and Doctor for a Treatment":
                    int treatmentId = Integer.parseInt(treatmentIdField.getText());
                    resultArea.setText(HospitalManagementSystem.listPatientAndDoctorForTreatment(treatmentId));
                    break;
                case "List All Workers":
                    resultArea.setText(HospitalManagementSystem.listAllWorkers());
                    break;
                case "List Primary Doctors with High Admissions":
                    resultArea.setText(HospitalManagementSystem.listPrimaryDoctorsWithHighAdmissions());
                    break;
                default:
                    resultArea.setText("No valid query selected.");
            }
            updateStatusLabel("Query executed successfully.", true);
        } catch (Exception e) {
            resultArea.setText("Error: " + e.getMessage());
            updateStatusLabel("Query execution failed.", false);
        }
    }


    @FXML
    private void handleQuerySelection() {
        // Show instructions or inputs based on selected query
        String selectedQuery = queryComboBox.getValue();
        switch (selectedQuery) {
            case "List Patients Admitted in a Date Range":
                resultArea.setText("Selected Query: List patients admitted within a specific date range. (Start Date and End Date required.)");
                break;
            case "List Patients Discharged in a Date Range":
                resultArea.setText("Selected Query: List patients discharged within a specific date range. (Start Date and End Date required.)");
                break;
            case "List Patients Admitted Within 30 Days of Last Discharge":
                resultArea.setText("Selected Query: List patients admitted within 30 days of their last discharge (No parameters required).");
                break;
            case "List Admission Statistics per Patient":
                resultArea.setText("Selected Query: List admission statistics per patient (No parameters required).");
                break;
            case "List Diagnoses with Highest Admissions":
                resultArea.setText("Selected Query: List diagnoses with the highest admissions (No parameters required).");
                break;
            case "List Patient and Doctor for a Treatment":
                resultArea.setText("Selected Query: List patient and doctor for a given treatment (Requires Treatment ID).");
                break;
            case "List All Workers":
                resultArea.setText("Selected Query: List all workers (No parameters required).");
                break;
            case "List Primary Doctors with High Admissions":
                resultArea.setText("Selected Query: List primary doctors with high admissions (No parameters required).");
                break;
            case "List Occupied Rooms":
            case "List Unoccupied Rooms":
            case "List All Rooms":
                resultArea.setText("Selected Query: " + selectedQuery);
                break;
            case "List Diagnoses by Occurrences":
            case "List Treatments by Occurrences":
                resultArea.setText("Selected Query: " + selectedQuery + " (No parameters required)");
                break;
            case "List All Patients":
            case "List Admitted Patients":
                resultArea.setText("Selected Query: " + selectedQuery + " (No parameters required)");
                break;
            default:
                resultArea.setText("Provide additional parameters for " + selectedQuery);
        }
    }


    @FXML
    private void clearResults() {
        resultArea.clear();
        statusLabel.setText("Ready");
        statusLabel.setStyle("-fx-text-fill: green;");
    }

    private void updateStatusLabel(String message, boolean isSuccess) {
        statusLabel.setText(message);
        statusLabel.setStyle(isSuccess ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
    }
}
