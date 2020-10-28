package org.example.wordcounter.controllers;

import org.example.wordcounter.App;
import org.example.wordcounter.exceptions.DownloadException;
import org.example.wordcounter.exceptions.ParsingException;
import org.example.wordcounter.services.CounterService;
import org.example.wordcounter.services.DownloadService;
import org.example.wordcounter.services.ParsingService;
import org.example.wordcounter.ui.ConsoleHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Главный контроллер приложения.
 * Обеспечивает порядок выполнения приложения
 */
public class MainController {
    private final Logger log = LoggerFactory.getLogger(App.class);

    private final ConsoleHelper consoleHelper;
    private final DownloadService downloadService;
    private final ParsingService parsingService;
    private final CounterService counterService;

    public MainController(ConsoleHelper consoleHelper,
                          DownloadService downloadService,
                          ParsingService parsingService,
                          CounterService counterService) {
        this.consoleHelper = consoleHelper;
        this.downloadService = downloadService;
        this.parsingService = parsingService;
        this.counterService = counterService;
    }

    /**
     * Выполняет основную логику приложения.
     * Выполняет обработку ошибок.
     *
     * @param urlStr   url-адрес html-страницы для скачивания
     * @param filename имя файла, для сохранения скачанной страницы
     */
    public void run(String urlStr, String filename) {

        try {

            downloadService.downloadUrl(urlStr, filename);

            Map<String, Integer> map = counterService.countWords(parsingService.parse(filename));

            consoleHelper.printMap(map);

        } catch (DownloadException e) {
            consoleHelper.print("Не удалось скачать страницу");
        } catch (ParsingException e) {
            consoleHelper.print(
                    "Не удалось извлечь контент страницы, возможно содержимое страницы не соотвествует формату html.");
        }

    }

}
