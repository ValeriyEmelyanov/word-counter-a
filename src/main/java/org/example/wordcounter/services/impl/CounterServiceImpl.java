package org.example.wordcounter.services.impl;

import org.example.wordcounter.services.CounterService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Реализует интефейс подсчета слов.
 */
public class CounterServiceImpl implements CounterService {

    /**
     * Подсчитывает количество уникальных слов в тексте (строке) без учета регистра.
     * Числа, номера телефонов и проценты исключает из списка слов.
     * @param text текст (строка), в котором должны быть подсчитаны слова
     * @return карта, содержащая уникальные слова в виде ключей, и их количество вхождений в тексте в качестве значений
     * @throws IOException
     */
    @Override
    public Map<String, Integer> countWords(String text) {

        // {' ', ',', '.', '!', '?','"', ';', ':', '[', ']', '(', ')', '\n', '\r', '\t'};
        final String regex = "\\s+[-–—]\\s+|[,.!?\";:\\[\\]()\n\r\t]\\s*|\\s+";
        String[] words = text.split(regex);

        Pattern pattern = Pattern.compile("^[\\d-]+%?$");

        Map<String, Integer> map = new HashMap<>();
        for (String s : words) {
            if (s.isEmpty() || pattern.matcher(s).matches()) {
                continue;
            }
            String key = s.toUpperCase();
            map.put(key, map.getOrDefault(key, 0) + 1);
        }

        return map;
    }

}
