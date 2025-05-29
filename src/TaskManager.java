import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskManager {
private static final String FILE_NAME = "tasks.json";
private List<Task> tasks;

private TaskManager() {
    this.tasks = loadTasks();
}

private List<Task> loadTasks(){
    List<Task> storedTasks = new ArrayList<>();

    File file = new File(FILE_NAME);
    if (!file.exists()) {
        return storedTasks;
    }

    StringBuilder jsonContent = new StringBuilder();

    
}


}