package org.example.wordcounter.controllers;

import org.example.wordcounter.exceptions.DaoException;
import org.example.wordcounter.exceptions.DbConnectionException;
import org.example.wordcounter.exceptions.DownloadException;
import org.example.wordcounter.exceptions.ParsingException;
import org.example.wordcounter.services.CounterService;
import org.example.wordcounter.services.DownloadService;
import org.example.wordcounter.services.ParsingService;
import org.example.wordcounter.services.ReviewRepositoryService;
import org.example.wordcounter.ui.ConsoleHelper;

import java.util.Map;

/**
 * Главный контроллер приложения.
 * Обеспечивает порядок выполнения приложения
 */
public class MainController {
    private final ConsoleHelper consoleHelper;
    private final DownloadService downloadService;
    private final ParsingService parsingService;
    private final CounterService counterService;
    private final ReviewRepositoryService repositoryService;

    public MainController(ConsoleHelper consoleHelper,
                          DownloadService downloadService,
                          ParsingService parsingService,
                          CounterService counterService, ReviewRepositoryService repositoryService) {
        this.consoleHelper = consoleHelper;
        this.downloadService = downloadService;
        this.parsingService = parsingService;
        this.counterService = counterService;
        this.repositoryService = repositoryService;
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

            try {
                repositoryService.save(urlStr, map);
            } catch (DbConnectionException | DaoException e) {
                consoleHelper.print(e.getMessage());
            }

            consoleHelper.printMap(map);

        } catch (DownloadException e) {
            consoleHelper.print("Не удалось скачать страницу");
        } catch (ParsingException e) {
            consoleHelper.print(
                    "Не удалось извлечь контент страницы, возможно содержимое страницы не соотвествует формату html.");
        }

    }

}
