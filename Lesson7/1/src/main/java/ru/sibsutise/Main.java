package ru.sibsutise;

import java.io.IOException;
import java.nio.file.*;
import java.util.Date;

public class Main {
    public static final void main (String arc[]) throws IOException, InterruptedException {
        Path file = Path.of("information.txt");
        Files.write(file,"".getBytes());


        WatchService watchService = FileSystems.getDefault().newWatchService();
        WatchKey key = Path.of("/home/dasha/Рабочий стол").register(watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.ENTRY_DELETE);

        while((key = watchService.take())!= null){
            for(WatchEvent <?> event : key.pollEvents()){
                Date data = new Date();

                String str = event.context() + "   " + event.kind().name() + "   "+ data + "\n";
                Files.write(file, str.getBytes(), StandardOpenOption.APPEND);
                System.out.println(event.context());
                System.out.println(event.kind().name());
            }
            key.reset();
        }

    }
}
