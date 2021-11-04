package me.rhys.uploader.app;

import lombok.Getter;
import me.rhys.uploader.config.ConfigManager;
import me.rhys.uploader.config.ConfigValues;
import me.rhys.uploader.gui.GUI;
import me.rhys.uploader.watcher.FileWatcher;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
public class App {
    @Getter
    private static App instance;

    private final GUI gui = new GUI();
    private final ConfigValues configValues = new ConfigValues();
    private final ConfigManager configManager = new ConfigManager();
    private final FileWatcher fileWatcher = new FileWatcher();
    private final ExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public App() {
        instance = this;

        this.configManager.load();
        this.gui.openGUI();
    }
}
