package org.example.wordcounter;

import org.example.wordcounter.controllers.MainController;
import org.example.wordcounter.services.impl.CounterServiceImpl;
import org.example.wordcounter.services.impl.DownloadServiceImpl;
import org.example.wordcounter.services.impl.ParsingServiceImpl;
import org.example.wordcounter.ui.ConsoleHelper;
import org.example.wordcounter.ui.impl.ConsoleHelperImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Главный класс приложения,
 * выполняет обработку параметров,
 * запускает главный контроллер приложения.
 */
public class App {
    private static final Logger log = LoggerFactory.getLogger(App.class);

    /**
     * Служит для запуска приложения.
     *
     * @param args входные аргументы (необязательные),
     *             первым аргументом ожидается url-адрес html-страницы для скачивания,
     *             вторым аргументом ожидается имя файла для сохранения скачанной html-страницы.
     */
    public static void main(String[] args) {
        ConsoleHelper consoleHelper = new ConsoleHelperImpl();

        String urlStr;
        if (args.length > 0) {
            urlStr = args[0];
        } else {
            urlStr = consoleHelper.readUrl();
        }
        if (urlStr.isEmpty()) {
            throw new RuntimeException("Url is empty!");
        }

        String filename;
        if (args.length > 1) {
            filename = args[1];
        } else {
            try {
                filename = File.createTempFile("downloaded", null).getPath();
            } catch (IOException e) {
                String msg = "Не удалось создать временный файл для сохранения страницы";
                consoleHelper.print(msg);
                log.error(msg);
                return;
            }
        }

        MainController controller = new MainController(
                consoleHelper,
                new DownloadServiceImpl(),
                new ParsingServiceImpl(),
                new CounterServiceImpl()
        );

        controller.run(urlStr, filename);
    }
}
