import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DatabaseConnector {
    private static final String PROPERTIES_FILE = "database.properties";

    private DatabaseConnector() {
        throw new UnsupportedOperationException();
    }

    public static Connection getConnection() throws SQLException {
        Properties properties = loadProperties();
        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");
        return DriverManager.getConnection(url, user, password);
    }

    private static Properties loadProperties() throws SQLException {
        Properties properties = new Properties();
        try (InputStream input = DatabaseConnector.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                throw new SQLException("Не найден файл настроек " + PROPERTIES_FILE);
            }
            properties.load(input);
        } catch (IOException e) {
            throw new SQLException("Не удалось прочитать файл настроек " + PROPERTIES_FILE, e);
        }
        return properties;
    }
}
