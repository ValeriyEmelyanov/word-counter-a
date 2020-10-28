package org.example.wordcounter.services.impl;

import org.example.wordcounter.services.ParsingService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

/**
 * Реализует извлечение контента html-страницы с помощью библиотеки Jsoup.
 */
public class ParsingServiceImpl implements ParsingService {

    /**
     * Извлекает контент из html-файла
     * @param filename имя файла
     * @return контент
     * @throws IOException
     */
    @Override
    public String parse(String filename) throws IOException {
        Document doc = Jsoup.parse(new File(filename), null);
        return doc.text();
    }

}
