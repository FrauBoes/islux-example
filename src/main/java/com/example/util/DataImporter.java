package com.example.util;

import com.example.model.Patient;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataImporter {

    private static final String DATA_PATH = "/data/medical_records.csv";
    private static final int ROW_COUNT = 2000;

    public List<Patient> importDataFromCSV() {
        List<Patient> patients = new ArrayList<>();
        InputStream inputStream = getClass().getResourceAsStream(DATA_PATH);

        if (inputStream != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                reader.readLine();  // skip first row (headers)
                int count = 1;
                while ((line = reader.readLine()) != null && count <= ROW_COUNT) {
                    String[] data = line.split(",");
                    int id = Integer.parseInt(data[0].trim());
                    String name = data[1].trim();
                    LocalDate dob = LocalDate.parse(data[2].trim(), DateFormatter.DATE_FORMATTER);
                    patients.add(new Patient(id, name, dob));
                    count++;
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Resource not found: " + DATA_PATH);
        }
        return patients;
    }
}

