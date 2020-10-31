package org.example.wordcounter.dao;

import org.example.wordcounter.exceptions.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Обработка сущностей Word в базе данных
 * Реализует рутинную работу с таблицей words посредством JDBC.
 */
public class WordsDao {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String SQL_CREATE = "CREATE TABLE words (" +
            "id IDENTITY NOT NULL," +
            "name VARCHAR(40) NOT NULL, " +
            "PRIMARY KEY (id)," +
            "UNIQUE (name))";
    private static final String SQL_SELECT =
            "SELECT id, name FROM words WHERE name IN (?)";
    private static final String SQL_INSERT =
            "INSERT INTO words (name) VALUES (?)";

    public void createTableWords() {
        DaoUtil.createTable(SQL_CREATE, "words");
    }

    /**
     * Строит карту соотвествий строкам (словам) и их уникальных идентификаторов.
     *
     * @param strings строки
     * @return карта соотвествий строк (слов) и их уникальных идентификаторов
     */
    public Map<String, Long> getWords(Set<String> strings) {
        Connection conn = DaoUtil.getConnection();

        String[] stringsArr = strings.toArray(new String[0]);

        Map<String, Long> result = new HashMap<>();

        final int batchSize = 1000;
        int shift = 0;
        int limit = Math.min(batchSize, stringsArr.length);

        String sql = SQL_SELECT.replaceFirst("\\?", String.join(",", "?".repeat(batchSize).split("")));

        try {
            while (limit > 0) {
                if (limit != batchSize) {
                    sql = SQL_SELECT.replaceFirst("\\?", String.join(",", "?".repeat(limit).split("")));
                }

                PreparedStatement statement = conn.prepareStatement(sql);
                for (int i = 0; i < limit; i++) {
                    statement.setString(i + 1, stringsArr[shift + i]);
                }

                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    result.put(resultSet.getString("name"), resultSet.getLong("id"));
                }

                shift += limit;
                limit = Math.min(batchSize, stringsArr.length - shift);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DaoException("Не удалось прочитать словарь слов.", e);
        }
        return result;
    }

    /**
     * Сохраняет в базу данных строки (слова).
     *
     * @param strings строки (слова)
     */
    public void saveWords(Set<String> strings) {
        Connection conn = DaoUtil.getConnection();

        try {
            conn.setAutoCommit(false);
            PreparedStatement statement = conn.prepareStatement(SQL_INSERT);

            final int batchSize = 1000;
            int shift = 0;
            int limit = Math.min(batchSize, strings.size());

            String[] stringsArr = strings.toArray(new String[0]);

            while (limit > 0) {
                for (int i = 0; i < limit; i++) {
                    statement.setString(1, stringsArr[shift + i]);
                    statement.addBatch();
                }

                statement.executeBatch();
                conn.commit();

                shift += limit;
                limit = Math.min(batchSize, strings.size() - shift);
            }

            conn.setAutoCommit(true);
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DaoException("Не удалось обновить словарь слов.", e);
        }
    }

}
