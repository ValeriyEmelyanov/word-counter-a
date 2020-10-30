package org.example.wordcounter.dao;

import org.example.wordcounter.exceptions.DaoException;
import org.example.wordcounter.exceptions.DbConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Утилита для работы с базой данных.
 * Использует механизм JDBC.
 */
public class DaoUtil {
    private static final Logger log = LoggerFactory.getLogger(DaoUtil.class);

    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:./statistics";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";

    private static volatile Connection connection;

    /**
     * Если необходимо устанавливает соединение с базой данных и возвращает его.
     *
     * @return соединение с базой данных
     */
    public static Connection getConnection() {
        if (connection == null) {
            synchronized (DaoUtil.class) {
                if (connection == null) {

                    try {
                        Class.forName(JDBC_DRIVER);
                    } catch (ClassNotFoundException e) {
                        log.error(e.getMessage());
                        throw new DbConnectionException("Ошибка загрузки драйвера базы данных", e);
                    }

                    try {
                        connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                    } catch (SQLException e) {
                        log.error(e.getMessage());
                        throw new DbConnectionException("Не удалось соединиться базой данных", e);
                    }
                }
            }
        }
        return connection;
    }

    /**
     * Закрывает соединение с базой данных.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ignored) {
                log.error(ignored.getMessage());
            }
        }
    }

    /**
     * Создает таблицу с указанным именем.
     *
     * @param sql       SQL-запрос для создания таблицы
     * @param tableName имя таблицы
     */
    public static void createTable(String sql, String tableName) {
        Connection conn = getConnection();

        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DaoException(
                    String.format("Не удалось создать таблицу \"%s\".", tableName),
                    e);
        }
    }

    /**
     * Проверяет существование таблицы с указанным именем.
     *
     * @param tableName имя таблицы
     * @return true если таблица существует, в противном случае false
     */
    public static boolean tableExists(String tableName) {
        Connection conn = DaoUtil.getConnection();
        try {
            ResultSet resultSet = conn.getMetaData().getTables(null, null, tableName.toUpperCase(), null);
            return resultSet.next();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DaoException(
                    String.format("Не удалось получить информацию о таблице \"%s\".", tableName),
                    e);
        }
    }

}
