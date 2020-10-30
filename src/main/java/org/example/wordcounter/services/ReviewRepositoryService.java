package org.example.wordcounter.services;

import java.util.Map;

/**
 * Сервис для работы с репозиторием рецензий.
 */
public interface ReviewRepositoryService {

    /**
     * Сохраняет результат рецензии / обзора - количество вхождений уникальных слов в контент.
     *
     * @param urlStr - url-адрес html-страницы
     * @param map    - количество вхождений уникальных слов в контент
     */
    void save(String urlStr, Map<String, Integer> map);

}
