package org.example.wordcounter.dao;

import org.example.wordcounter.entities.Review;
import org.example.wordcounter.entities.Word;
import org.example.wordcounter.exceptions.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;

/**
 * Обработка сущностей Reviews в базе данных.
 * Реализует рутинную работу с таблицей reviews и reviews_items  посредством JDBC.
 */
public class ReviewDao {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String SQL_CREATE_REVIEWS = "CREATE TABLE reviews (" +
            "id IDENTITY NOT NULL," +
            "url VARCHAR(250) NOT NULL, " +
            "review_date TIMESTAMP, " +
            "PRIMARY KEY (id))";
    private static final String SQL_CREATE_REVIEW_ITEMS = "CREATE TABLE review_items (" +
            "id IDENTITY NOT NULL," +
            "id_review BIGINT NOT NULL, " +
            "id_word BIGINT NOT NULL, " +
            "amount INT, " +
            "PRIMARY KEY (id), " +
            "FOREIGN KEY (id_review) REFERENCES reviews, " +
            "FOREIGN KEY (id_word) REFERENCES words)";
    private static final String SQL_INSERT_REVIEWS =
            "INSERT INTO reviews (url,review_date) VALUES (?,?)";
    private static final String SQL_SELECT_REVIEWS =
            "SELECT id FROM reviews WHERE url=? AND review_date=?";
    private static final String SQL_INSERT_REVIEW_ITEMS =
            "INSERT INTO review_items (id_review,id_word,amount) VALUES (?,?,?)";

    /**
     * Сохранение рецензии в базе данны.
     *
     * @param review рецензия
     */
    public void save(Review review) {

        Connection conn = DaoUtil.getConnection();

        try {
            PreparedStatement statement = conn.prepareStatement(SQL_INSERT_REVIEWS);
            statement.setString(1, review.getUrl());
            String timesSource = Timestamp.valueOf(review.getReviewDate()).toString();
            String times = timesSource.substring(0, timesSource.length() - 4);
            Timestamp timestamp = Timestamp.valueOf(times);
            statement.setTimestamp(2, timestamp);
            statement.executeUpdate();

            PreparedStatement statementSelect = conn.prepareStatement(SQL_SELECT_REVIEWS);
            statementSelect.setString(1, review.getUrl());
            statementSelect.setTimestamp(2, timestamp);
            ResultSet resultSet = statementSelect.executeQuery();
            long idReviews;
            if (resultSet.next()) {
                idReviews = resultSet.getLong("id");
            } else {
                log.error("Couldn't save the review result");
                throw new DaoException("Не удалось сохранить рецензию.");
            }

            conn.setAutoCommit(false);
            PreparedStatement statementItem = conn.prepareStatement(SQL_INSERT_REVIEW_ITEMS);
            for (Map.Entry<Word, Integer> entry : review.getItems().entrySet()) {
                statementItem.setLong(1, idReviews);
                statementItem.setLong(2, entry.getKey().getId());
                statementItem.setInt(3, entry.getValue());
                statementItem.addBatch();
            }
            statementItem.executeBatch();

            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DaoException("Не удалось сохранить результат рецензии.", e);
        }

    }

    /**
     * Создает таблицу рецензий reviews.
     */
    public void createTableReviews() {
        DaoUtil.createTable(SQL_CREATE_REVIEWS, "reviews");
    }

    /**
     * Создает таблицу записей рецензий review_items.
     */
    public void createTableReviewItems() {
        DaoUtil.createTable(SQL_CREATE_REVIEW_ITEMS, "review_items");
    }
}
