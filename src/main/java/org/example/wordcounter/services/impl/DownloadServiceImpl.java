package org.example.wordcounter.services.impl;

import org.example.wordcounter.services.DownloadService;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static java.net.http.HttpClient.*;

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

        /* Вариант с URL
        URL url = new URL(urlStr);
        Files.copy(url.openStream(), Paths.get(filename), StandardCopyOption.REPLACE_EXISTING);
         */

        HttpClient client = newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(urlStr))
                .GET()
                .build();

        HttpResponse<InputStream> inputStreamHttpResponse = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
        Files.copy(inputStreamHttpResponse.body(), Paths.get(filename), StandardCopyOption.REPLACE_EXISTING);

    }
}
