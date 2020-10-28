package org.example.wordcounter.services;

import java.io.IOException;

/**
 * Интерфес отвечающий за извлечение контента из html-файла
 */
public interface ParsingService {

    /**
     * Извлекает контент из html-файла
     * @param filename имя файла
     * @return контент
     * @throws IOException
     */
    String parse(String filename) throws IOException;

}
