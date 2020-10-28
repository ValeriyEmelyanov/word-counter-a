package org.example.wordcounter.services.impl;

import org.example.wordcounter.exceptions.ParsingException;
import org.example.wordcounter.services.ParsingService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Реализует извлечение контента html-страницы с помощью библиотеки Jsoup.
 */
public class ParsingServiceImpl implements ParsingService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Извлекает контент из html-файла
     * @param filename имя файла
     * @return контент
     */
    @Override
    public String parse(String filename) {

        try {
            Document doc = Jsoup.parse(new File(filename), null);
            return doc.text();
        } catch (IOException e) {
            log.error(
                    "Не удалось извлечь контент из файла {}, возможно содержимое файла не соотвествует формату html.",
                    filename);
            throw new ParsingException(e);
        }

    }

}
