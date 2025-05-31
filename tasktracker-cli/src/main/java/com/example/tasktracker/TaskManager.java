import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            String json = mapper.writeValueAsString(tasks);
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}