package org.example.wordcounter.services;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Интерфейс скачивания файла из Internet
 */
public interface DownloadService {

    /**
     * Скачивает файл
     * @param urlStr адрес файла
     * @param filename имя файла для сохранения на диск
     * @throws IOException
     * @throws URISyntaxException
     * @throws InterruptedException
     */
    void downloadUrl(String urlStr, String filename) throws IOException, URISyntaxException, InterruptedException;

}
