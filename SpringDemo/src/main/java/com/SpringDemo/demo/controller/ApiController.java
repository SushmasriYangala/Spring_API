package com.SpringDemo.demo.controller;

import com.SpringDemo.demo.model.DataModel;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    private static final String JSON_FILE_PATH = "data.json";
    private ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/data")
    public List<DataModel> fetchData() {
        List<DataModel> data = readDataFromJson();
        return data;
    }

    @PostMapping("/submit")
    public String submitData(@RequestParam("name") String name, @RequestParam("age") int age) {
        DataModel newData = new DataModel();
        newData.setName(name);
        newData.setAge(age);
        writeDataToJson(newData);
        return "Data submitted successfully";
    }

    private List<DataModel> readDataFromJson() {
        try {
            File file = new File(JSON_FILE_PATH);
            if (!file.exists()) {
                return new ArrayList<>(); // Return empty list if file doesn't exist yet
            }
            return objectMapper.readValue(file, new TypeReference<List<DataModel>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>(); // Return empty list in case of error
        }
    }

    private void writeDataToJson(DataModel newData) {
        try {
            List<DataModel> existingData = readDataFromJson();
            existingData.add(newData);
            objectMapper.writeValue(new File(JSON_FILE_PATH), existingData);
        } catch (IOException e) {
            e.printStackTrace(); // Prints an error occurs during reading or writing data to the JSON file
        }
    }
}
