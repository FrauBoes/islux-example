package com.example.controllers;

import com.example.model.Patient;
import com.example.util.DataImporter;
import com.example.util.DatabaseManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static com.example.util.DateFormatter.DATE_FORMATTER;
import static com.example.util.DateFormatter.DATE_PATTERN;

public class Controller {
    @FXML
    public TextField nameTextField;

    @FXML
    public TextField dobTextField;

    @FXML
    public Button insertButton;

    @FXML
    public Button updateButton;

    @FXML
    public Button deleteButton;

    @FXML
    private ListView<Patient> patientListView;

    @FXML
    private Button importButton;

    @FXML
    private Button refreshButton;

    private final DatabaseManager databaseManager;

    private Patient selectedPatient;

    public Controller() {
        databaseManager = new DatabaseManager();
    }

    @FXML
    public void initialize() {
        refreshList();
        patientListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedPatient = newValue;
            if (selectedPatient != null) {
                nameTextField.setText(selectedPatient.getName());
                dobTextField.setText(selectedPatient.getDob().format(DATE_FORMATTER));
            }
        });
    }

    @FXML
    private void insertPatient() {
        String name = nameTextField.getText();
        String dob = dobTextField.getText();
        if (!name.isEmpty() && !dob.isEmpty()) {
            try {
                databaseManager.insertPatient(name, parseDate(dob));
            } catch (DateTimeParseException e) {
                showAlert("Invalid date format",
                        "Please enter date of birth in the following format: " + DATE_PATTERN);
            } finally {
                refreshList();
                clearFields();
            }
        } else {
            showAlert("Missing information",
                    "Please enter name and date of birth.");
        }
    }

    @FXML
    private void updatePatient() {
        if (selectedPatient != null) {
            String newName = nameTextField.getText();
            String newDob = dobTextField.getText();
            if (!newName.isEmpty() && !newDob.isEmpty()) {
                try {
                    databaseManager.updatePatient(selectedPatient.getId(), newName, parseDate(newDob));
                } catch (DateTimeParseException e) {
                    showAlert("Invalid date format",
                            "Please enter date of birth in the following format: " + DATE_PATTERN);
                } finally {
                    refreshList();
                    clearFields();
                }
            } else {
                showAlert("Missing information",
                        "Please enter name and date of birth.");
            }
        } else {
            showAlert("No Patient Selected",
                    "Please select a patient to update.");
        }
    }

    @FXML
    private void deletePatient() {
        if (selectedPatient != null) {
            databaseManager.deletePatient(selectedPatient.getId());
            refreshList();
            clearFields();
        } else {
            showAlert("No Patient Selected",
                    "Please select a patient to update.");
        }
    }

    @FXML
    private void importData() {
        DataImporter dataImporter = new DataImporter();
        databaseManager.createTable();
        dataImporter.importDataFromCSV()
                .forEach(patient ->
                        databaseManager.insertPatient(patient.getName(), patient.getDob(), patient.getId()));
        refreshList();
    }

    @FXML
    private void refreshList() {
        patientListView.getItems().clear();
        patientListView.getItems().addAll(databaseManager.getAllPatients());

        patientListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Patient patient, boolean empty) {
                super.updateItem(patient, empty);
                if (empty || patient == null) {
                    setText(null);
                } else {
                    setText(String.format("ID: %d | Name: %s | Dob: %s",
                            patient.getId(), patient.getName(), DATE_FORMATTER.format(patient.getDob())));
                }
            }
        });
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        nameTextField.clear();
        dobTextField.clear();
    }

    private LocalDate parseDate(String date) {
        return LocalDate.parse(date, DATE_FORMATTER);
    }
}

