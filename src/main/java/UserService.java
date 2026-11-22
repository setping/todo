import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {
    private static final int USER_NOT_FOUND = -1;

    private static final String SELECT_USER_ID = "SELECT id FROM \"user\" WHERE name = ?";
    private static final String INSERT_USER = "INSERT INTO \"user\" (name) VALUES (?) RETURNING id";

    public int saveUser(String name) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            int existingId = findUserId(connection, name);
            if (existingId != USER_NOT_FOUND) {
                return existingId;
            }
            return insertUser(connection, name);
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось сохранить пользователя: " + e.getMessage(), e);
        }
    }

    private int findUserId(Connection connection, String name) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_USER_ID)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
                return USER_NOT_FOUND;
            }
        }
    }

    private int insertUser(Connection connection, String name) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_USER)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt("id");
            }
        }
    }
}
