import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnector {
    private static final String DEFAULT_URL = "jdbc:postgresql://localhost:5432/task_manager";
    private static final String DEFAULT_USER = "postgres";

    private DatabaseConnector() {
        throw new UnsupportedOperationException();
    }

    public static Connection getConnection() throws SQLException {
        String password = System.getenv("DB_PASSWORD");
        if (password == null) {
            throw new SQLException("Не задан пароль от базы: переменная окружения DB_PASSWORD");
        }

        String url = getEnvOrDefault("DB_URL", DEFAULT_URL);
        String user = getEnvOrDefault("DB_USER", DEFAULT_USER);
        return DriverManager.getConnection(url, user, password);
    }

    private static String getEnvOrDefault(String name, String defaultValue) {
        String value = System.getenv(name);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return value;
    }
}
