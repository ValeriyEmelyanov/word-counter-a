package org.example.wordcounter.ui;

import java.util.Map;

/**
 * Интерфейс пользователя.
 */
public interface ConsoleHelper {

    /**
     * Выводит сообщения.
     *
     * @param msg сообщение
     */
    void print(String msg);

    /**
     * Читает url-адрес.
     *
     * @return url-адрес
     */
    String readUrl();

    /**
     * Выводит карту.
     *
     * @param map карта
     */
    void printMap(Map<String, Integer> map);
}
