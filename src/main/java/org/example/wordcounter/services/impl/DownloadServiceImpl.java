package org.example.wordcounter.services.impl;

import org.example.wordcounter.exceptions.DownloadException;
import org.example.wordcounter.services.DownloadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Реализует интерфейс скачивания файла с помощью HTTP запроса.
 */
public class DownloadServiceImpl implements DownloadService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Скачивает файл
     *
     * @param urlStr   адрес файла
     * @param filename имя файла для сохранения на диск
     */
    @Override
    public void downloadUrl(String urlStr, String filename) {

        try {
            URL url = new URL(urlStr);
            Files.copy(url.openStream(), Paths.get(filename), StandardCopyOption.REPLACE_EXISTING);
        } catch (MalformedURLException e) {
            log.error("Неверный url-адрес: {}", urlStr);
            throw new DownloadException(e);
        } catch (IOException e) {
            log.error("Ошибка потока ввода-вывода: {} - {}", urlStr, filename);
            throw new DownloadException(e);
        }

    }
}
