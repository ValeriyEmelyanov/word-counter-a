package org.example.wordcounter.repositories.impl;

import org.example.wordcounter.dao.DaoUtil;
import org.example.wordcounter.dao.ReviewDao;
import org.example.wordcounter.dao.WordsDao;
import org.example.wordcounter.entities.Review;
import org.example.wordcounter.entities.Word;
import org.example.wordcounter.repositories.ReviewRepository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Реализация репозитория рецензий.
 */
public class ReviewRepositoryImpl implements ReviewRepository {
    private final WordsDao wordsDao = new WordsDao();
    private final ReviewDao reviewDao = new ReviewDao();

    /**
     * Сохраняет результат рецензии / обзора - количество вхождений уникальных слов в контент.
     *
     * @param review - рецензия
     */
    @Override
    public void save(Review review) {
        if (!DaoUtil.tableExists("reviews")) {
            reviewDao.createTableReviews();
        }
        if (!DaoUtil.tableExists("review_items")) {
            reviewDao.createTableReviewItems();
        }
        reviewDao.save(review);
    }

    /**
     * Строит карту соотвествий строк сущностям слова.
     *
     * @param strings уникальные слова контента
     * @return карта соотвествий строк сущностям слова
     */
    @Override
    public Map<String, Word> getWords(Set<String> strings) {

        Set<String> rookies = new HashSet<>(strings);
        Map<String, Word> result = new HashMap<>();

        if (!DaoUtil.tableExists("words")) {
            wordsDao.createTableWords();
        } else {
            Map<String, Long> registred = wordsDao.getWords(strings);
            for (Map.Entry<String, Long> entry : registred.entrySet()) {
                result.put(entry.getKey(), new Word(entry.getValue(), entry.getKey()));
            }
            rookies.removeAll(registred.keySet());
        }

        if (rookies.size() > 0) {
            wordsDao.saveWords(rookies);
            Map<String, Long> saved = wordsDao.getWords(rookies);

            for (Map.Entry<String, Long> entry : saved.entrySet()) {
                result.put(entry.getKey(), new Word(entry.getValue(), entry.getKey()));
            }
        }

        return result;
    }

    @Override
    public void closeResource() {
        DaoUtil.closeConnection();
    }

}
