package org.example.wordcounter.services;

/**
 * Интерфейс скачивания файла из Internet
 */
public interface DownloadService {

    /**
     * Скачивает файл
     *
     * @param urlStr   адрес файла
     * @param filename имя файла для сохранения на диск
     */
    void downloadUrl(String urlStr, String filename);

}
