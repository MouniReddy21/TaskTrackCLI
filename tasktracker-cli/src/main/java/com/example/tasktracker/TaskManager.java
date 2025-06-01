package com.example.tasktracker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TaskManager {

    private static final String FILE_NAME = "tasks.json";
    private List<Task> tasks;
    private ObjectMapper mapper;

    public TaskManager() {
        mapper = new ObjectMapper();
        // Register module to support Java 8 Date/Time API
        mapper.registerModule(new JavaTimeModule());
        // Optional: pretty print JSON output
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        // Prevent Jackson from writing timestamps as arrays
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        this.tasks = loadTasks();
    }

    private List<Task> loadTasks() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

            Task[] taskArray = mapper.readValue(jsonContent.toString(), Task[].class);
            return new ArrayList<>(Arrays.asList(taskArray));
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Optional<Task> findTask(int id) {
        return tasks.stream()
                .filter(task -> task.getId() == id)
                .findFirst();
    }

    public void saveTasks() {
        // If you wanted to add only new entries to the file without overwriting (like a log), you’d do this
        // new FileWriter(FILE_NAME, true)  // 'true' enables append mode
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            String json = mapper.writeValueAsString(tasks);
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // add tasks
    public void addTask(Task task){
        int taskid = tasks.stream().mapToInt(t -> t.getId()).max().orElse(0) + 1;
        task.setId(taskid); 
        tasks.add(task);
        System.out.println("Task added successfully (ID: " + task.getId() + ")");
    }

    public void updateTask(int id, String newName){
        Task task = findTask(id).orElseThrow(() -> new IllegalArgumentException("Task with ID " + id + " not found!"));
        task.updateDescription(newName);
        System.out.println("Task " + task.getId() + " updated");
    }

    public void deleteTask(int id){
        Task task = findTask(id).orElseThrow(() -> new IllegalArgumentException("Task with ID " + id + " not found!"));
        tasks.remove(task);
    }

    public void markTask(int id, StatusType status){
        Task task = findTask(id).orElseThrow(() -> new IllegalArgumentException("Task with ID " + id + " not found!"));
        task.setStatus(status);
        System.out.println("Task marked as " + status.name().toLowerCase().replace("_", "-"));

    }

    private void printTask(Task task) {
        System.out.println("ID: " + task.getId() + " | " + task.getDescription() + " | Status: " + task.getStatus());
    }

    public void listAll() {
        tasks.forEach(this::printTask);
    }

    public void listByStatus(StatusType status) {
        try {
            // Task.Status status = Task.Status.valueOf(statusStr.toUpperCase());
            tasks.stream()
                 .filter(t -> t.getStatus() == status)
                 .forEach(this::printTask);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid status: " + status.toString());
        }
    }



}