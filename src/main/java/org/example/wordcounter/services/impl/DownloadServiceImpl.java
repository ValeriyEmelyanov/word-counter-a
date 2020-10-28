package org.example.wordcounter.services.impl;

import org.example.wordcounter.services.DownloadService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Реализует интерфейс скачивания файла с помощью HTTP запроса.
 */
public class DownloadServiceImpl implements DownloadService {

    /**
     * Скачивает файл
     * @param urlStr адрес файла
     * @param filename имя файла для сохранения на диск
     * @throws IOException
     * @throws URISyntaxException
     * @throws InterruptedException
     */
    @Override
    public void downloadUrl(String urlStr, String filename) throws IOException, URISyntaxException, InterruptedException {

        URL url = new URL(urlStr);
        Files.copy(url.openStream(), Paths.get(filename), StandardCopyOption.REPLACE_EXISTING);

        /* Вариант с HttpClient, не работает с адресами вида file:/
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(urlStr))
                .GET()
                .build();

        HttpResponse<InputStream> inputStreamHttpResponse = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
        Files.copy(inputStreamHttpResponse.body(), Paths.get(filename), StandardCopyOption.REPLACE_EXISTING);
         */
    }
}
