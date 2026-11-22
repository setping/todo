import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseTaskHandler implements TaskHandler {
    private static final String SELECT_TASKS =
            "SELECT id, name, priority, description, deadline, status FROM task WHERE user_id = ? ORDER BY id";
    private static final String INSERT_TASK =
            "INSERT INTO task (name, priority, description, deadline, status, user_id) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String DELETE_TASK = "DELETE FROM task WHERE id = ? AND user_id = ?";
    private static final String COMPLETE_TASK = "UPDATE task SET status = TRUE WHERE id = ? AND user_id = ?";

    private final int userId;

    public DatabaseTaskHandler(int userId) {
        this.userId = userId;
    }

    @Override
    public List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_TASKS)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    tasks.add(mapTask(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось получить задачи из базы: " + e.getMessage(), e);
        }
        return tasks;
    }

    @Override
    public void addTask(Task task) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_TASK)) {
            statement.setString(1, task.getName());
            statement.setInt(2, task.getPriority());
            statement.setString(3, task.getDescription());
            statement.setString(4, task.getDeadline());
            statement.setBoolean(5, task.isStatus());
            statement.setInt(6, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось сохранить задачу в базу: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean deleteTask(int id) {
        return updateTaskById(DELETE_TASK, id);
    }

    @Override
    public boolean completeTask(int id) {
        return updateTaskById(COMPLETE_TASK, id);
    }

    private boolean updateTaskById(String sql, int id) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setInt(2, userId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось изменить задачу в базе: " + e.getMessage(), e);
        }
    }

    private Task mapTask(ResultSet resultSet) throws SQLException {
        Task task = new Task(
                resultSet.getInt("priority"),
                resultSet.getString("deadline"),
                resultSet.getString("description"),
                resultSet.getString("name"),
                resultSet.getInt("id"));
        task.setStatus(resultSet.getBoolean("status"));
        return task;
    }
}
