package me.rhys.uploader.config;

import me.rhys.uploader.app.App;
import me.rhys.uploader.util.Logger;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {
    private final File CONFIG_FILE = new File("config.json");

    public void load() {
        if (this.CONFIG_FILE.exists()) {
            Logger.log("Loading config...");

            try {
                JSONObject jsonObject = new JSONObject(new String(Files.readAllBytes(this.CONFIG_FILE.toPath())));

                if (jsonObject.has("IP")) {
                    App.getInstance().getConfigValues().setFtpIP(jsonObject.getString("IP"));
                }

                if (jsonObject.has("Port")) {
                    App.getInstance().getConfigValues().setFtpPort(Integer.parseInt(jsonObject.getString("Port")));
                }

                if (jsonObject.has("Username")) {
                    App.getInstance().getConfigValues().setFtpUsername(jsonObject.getString("Username"));
                }

                if (jsonObject.has("Password")) {
                    App.getInstance().getConfigValues().setFtpPassword(jsonObject.getString("Password"));
                }

                Logger.log("Loaded config.json");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Logger.warn("no config.json file found!");
        }
    }

    public void saveConfig() {

        Logger.log("Saving config.json");

        Map<String, String> map = new HashMap<>();
        map.put("IP", App.getInstance().getConfigValues().getFtpIP());
        map.put("Port", String.valueOf(App.getInstance().getConfigValues().getFtpPort()));
        map.put("Username", App.getInstance().getConfigValues().getFtpUsername());
        map.put("Password", App.getInstance().getConfigValues().getFtpPassword());

        try {
            FileWriter fileWriter = new FileWriter(this.CONFIG_FILE.getAbsolutePath());
            fileWriter.write(new JSONObject(map).toString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        map.clear();
        Logger.log("Saved config.json!");
    }

    public void setConnectionValues(String IP, int port, String username, String password) {
        App.getInstance().getConfigValues().setFtpIP(IP);
        App.getInstance().getConfigValues().setFtpPort(port);
        App.getInstance().getConfigValues().setFtpUsername(username);
        App.getInstance().getConfigValues().setFtpPassword(password);
    }
}
