package org.example.wordcounter.services.impl;

import org.example.wordcounter.services.CounterService;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тест сервиса CounterServiceImpl.
 */
class CounterServiceImplTest {

    /**
     * Проверяет работу метода по подсчету уникальных слов countWords().
     */
    @Test
    void countWords() {
        String text = "Каждый охотник 1 желает знать. - Знать, где сидит фазан; фазан? фазан!!! 777-22-22";

        CounterService service = new CounterServiceImpl();
        Map<String, Integer> actual = service.countWords(text);

        HashMap<String, Integer> expected = new HashMap<>();
        expected.put("КАЖДЫЙ", 1);
        expected.put("ОХОТНИК", 1);
        expected.put("ЖЕЛАЕТ", 1);
        expected.put("ЗНАТЬ", 2);
        expected.put("ГДЕ", 1);
        expected.put("СИДИТ", 1);
        expected.put("ФАЗАН", 3);

        assertEquals(expected, actual);
    }
}