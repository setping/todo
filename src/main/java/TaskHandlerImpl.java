import java.util.List;
import java.util.ArrayList;

public class TaskHandlerImpl implements TaskHandler {
    private List<Task> tasks =  new ArrayList<>();

    @Override
    public List<Task> getTasks() {
        return tasks;
    }

    @Override
    public void addTask(Task task) {
        tasks.add(task);
    }

    @Override
    public boolean deleteTask(int id) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == id) {
                tasks.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean completeTask(int id) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == id) {
                tasks.get(i).setStatus(true);
                return true;
            }
        }
        return false;
    }
}
