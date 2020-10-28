package org.example.wordcounter.ui.impl;

import org.example.wordcounter.ui.ConsoleHelper;

import java.util.Map;
import java.util.Scanner;

/**
 * Реализует интерфейс пользователя через стандартную консоль.
 */
public class ConsoleHelperImpl implements ConsoleHelper {
    private final Scanner scanner;

    public ConsoleHelperImpl() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Выводит сообщения в консоль.
     * @param msg сообщение
     */
    @Override
    public void print(String msg) {
        System.out.println(msg);
    }

    /**
     * Запрашивает и читает url-адресс консоли.
     * @return url-адрес
     */
    @Override
    public String readUrl() {
        System.out.print("Enter URL: ");
        return scanner.nextLine();
    }

    /**
     * Выводит карту в виде каждое "ключ - значение" в отдельной строке в консоль.
     * @param map карта
     */
    @Override
    public void printMap(Map<String, Integer> map) {
        map.forEach((key, value) -> System.out.printf("%s - %d\n", key, value));
    }
}
