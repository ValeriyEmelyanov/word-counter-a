package org.example.wordcounter.entities;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Рецензия url-адреса, содержащая статистику:
 * - количество вхождений в контент уникальных слов.
 */
public class Review {
    private long id;
    private String url;
    private LocalDateTime reviewDate;
    private Map<Word, Integer> items;

    public Review() {
    }

    public Review(int id, String url, LocalDateTime reviewDate) {
        this.id = id;
        this.url = url;
        this.reviewDate = reviewDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }

    public Map<Word, Integer> getItems() {
        return items;
    }

    public void setItems(Map<Word, Integer> items) {
        this.items = items;
    }
}
