import java.util.List;

public interface TaskHandler {
    List<Task> getTasks();
    void addTask(Task task);
    boolean deleteTask(int id);
    boolean completeTask(int id);
}
