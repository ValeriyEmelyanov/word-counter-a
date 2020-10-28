package org.example.wordcounter.services;

/**
 * Интерфес отвечающий за извлечение контента из html-файла
 */
public interface ParsingService {

    /**
     * Извлекает контент из html-файла
     *
     * @param filename имя файла
     * @return контент
     */
    String parse(String filename);

}
