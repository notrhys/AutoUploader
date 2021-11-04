package me.rhys.uploader.watcher;

import lombok.Getter;
import me.rhys.uploader.app.App;
import me.rhys.uploader.util.Logger;

import java.awt.*;
import java.io.File;
import java.nio.file.*;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class FileWatcher {

    private final File OUTPUT = new File("output/");
    private final UploaderService SERVICE = new UploaderService();

    private long lastUpload;

    public void start(String serverDirectory) {
        Logger.log("Starting...");

        this.OUTPUT.mkdirs();

        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path path = this.OUTPUT.toPath();
            WatchKey watchKey = path.register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY);

            Logger.log("Started file watcher!");

            App.getInstance().getGui().getStatusLabel().setForeground(Color.GREEN);
            App.getInstance().getGui().getStatusLabel().setText("Status: Running!");

            while (true) {
                WatchKey key = watchService.take();

                if (key != null) {
                    long now = System.currentTimeMillis();

                    key.pollEvents().forEach(watchEvent -> {
                        if ((now - this.lastUpload) < 1000L) return;
                        this.lastUpload = now;


                        File contextFile = new File(this.OUTPUT.getAbsolutePath() + "/" +
                                watchEvent.context().toString());

                        Logger.log("Detected a file change in "
                                + this.OUTPUT.getAbsolutePath() + " "
                                + contextFile.getAbsolutePath() + " "
                                + watchEvent.kind().name());

                        if (contextFile.exists()) {
                            Logger.log("File change is valid, uploading..." +
                                    " (" + contextFile.getAbsolutePath() + ")");

                            new Thread(() -> this.SERVICE.start(contextFile, serverDirectory)).start();
                        } else {
                            Logger.log("File change was not valid, ignoring!" +
                                    " (" + contextFile.getAbsolutePath() + ")");
                        }
                    });

                    watchKey.reset();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
