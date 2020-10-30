package org.example.wordcounter.services.impl;

import org.example.wordcounter.entities.Review;
import org.example.wordcounter.entities.Word;
import org.example.wordcounter.repositories.ReviewRepository;
import org.example.wordcounter.services.ReviewRepositoryService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Репозиторий рецензий.
 */
public class ReviewRepositoryServiceImpl implements ReviewRepositoryService {
    private final ReviewRepository reviewRepository;

    public ReviewRepositoryServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    /**
     * Сохраняет результат рецензии / обзора - количество вхождений уникальных слов в контент.
     *
     * @param urlStr - url-адрес html-страницы
     * @param map    - количество вхождений уникальных слов в контент
     */
    @Override
    public void save(String urlStr, Map<String, Integer> map) {

        Map<String, Word> wordsMap = reviewRepository.getWords(map.keySet());

        Map<Word, Integer> items = new HashMap<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            items.put(wordsMap.get(entry.getKey()), entry.getValue());
        }

        Review review = new Review();
        review.setUrl(urlStr);
        review.setItems(items);
        review.setReviewDate(LocalDateTime.now());

        reviewRepository.save(review);
    }

}
