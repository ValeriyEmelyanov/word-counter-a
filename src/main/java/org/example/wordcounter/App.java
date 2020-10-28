package org.example.wordcounter;

import org.example.wordcounter.controllers.MainController;
import org.example.wordcounter.services.impl.CounterServiceImpl;
import org.example.wordcounter.services.impl.DownloadServiceImpl;
import org.example.wordcounter.services.impl.ParsingServiceImpl;
import org.example.wordcounter.ui.ConsoleHelper;
import org.example.wordcounter.ui.impl.ConsoleHelperImpl;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws InterruptedException, IOException, URISyntaxException {
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
            filename = File.createTempFile("downloaded", null).getPath();
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
