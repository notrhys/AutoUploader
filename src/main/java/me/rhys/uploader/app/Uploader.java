package me.rhys.uploader.app;

import lombok.Getter;
import me.rhys.uploader.app.protocol.SFTPProtocol;
import me.rhys.uploader.util.Logger;

import java.io.File;
import java.nio.file.*;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class Uploader {

    private final File OUTPUT_FOLDER = new File("output/");
    private final SFTPProtocol PROTOCOL = new SFTPProtocol();

    private long lastUpload;

    public Uploader() {
        this.OUTPUT_FOLDER.mkdirs();
        this.start();
    }

    void start() {
        Logger.log("Waiting for files...");

        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path path = this.OUTPUT_FOLDER.toPath();
            WatchKey watchKey = path.register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY);

            //noinspection InfiniteLoopStatement
            while (true) {
                WatchKey key = watchService.take();

                if (key != null) {
                    long now = System.currentTimeMillis();

                    key.pollEvents().forEach(watchEvent -> {
                        if ((now - this.lastUpload) < 1000L) return;
                        this.lastUpload = now;


                        File contextFile = new File(this.OUTPUT_FOLDER.getAbsolutePath() + "/" +
                                watchEvent.context().toString());

                        Logger.log("Detected a file change in "
                                + this.OUTPUT_FOLDER.getAbsolutePath() + " "
                                + contextFile.getAbsolutePath() + " "
                                + watchEvent.kind().name());

                        if (contextFile.exists()) {
                            Logger.log("File change is valid, uploading..." +
                                    " (" + contextFile.getAbsolutePath() + ")");

                            new Thread(() -> this.PROTOCOL.start(contextFile)).start();
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
