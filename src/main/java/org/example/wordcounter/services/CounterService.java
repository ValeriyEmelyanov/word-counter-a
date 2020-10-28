package org.example.wordcounter.services;

import java.io.IOException;
import java.util.Map;

/**
 * Интерфейс подсчета слов.
 */
public interface CounterService {

    /**
     * Подсчитывает количество уникальных слов в тексте (строке).
     * @param text текст (строка), в котором должны быть подсчитаны слова
     * @return карта, содержащая уникальные слова в виде ключей, и их количество вхождений в тексте в качестве значений
     * @throws IOException
     */
    Map<String, Integer> countWords(String text) throws IOException;

}
