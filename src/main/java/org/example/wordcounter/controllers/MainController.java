package org.example.wordcounter.controllers;

import org.example.wordcounter.services.CounterService;
import org.example.wordcounter.services.DownloadService;
import org.example.wordcounter.services.ParsingService;
import org.example.wordcounter.ui.ConsoleHelper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class MainController {
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

    public void run(String urlStr, String filename) throws IOException, URISyntaxException, InterruptedException {

        downloadService.downloadUrl(urlStr, filename);

        Map<String, Integer> map = counterService.countWords(parsingService.parse(filename));

        consoleHelper.printMap(map);

    }

}
