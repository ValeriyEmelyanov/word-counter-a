package org.example.wordcounter.repositories;

import org.example.wordcounter.entities.Review;
import org.example.wordcounter.entities.Word;

import java.util.Map;
import java.util.Set;

/**
 * Интерфейс репозитория рецензий
 */
public interface ReviewRepository {

    /**
     * Сохраняет результат рецензии / обзора - количество вхождений уникальных слов в контент.
     *
     * @param review - рецензия
     */
    void save(Review review);

    /**
     * Строит карту соотвествий строк сущностям слова.
     *
     * @param strings уникальные слова контента
     * @return карта соотвествий строк сущностям слова
     */
    Map<String, Word> getWords(Set<String> strings);

    /**
     * Закрывает / освобождает ресурсы.
     */
    void closeResource();

}
