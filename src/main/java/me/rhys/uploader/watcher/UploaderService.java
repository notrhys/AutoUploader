package me.rhys.uploader.watcher;

import me.rhys.uploader.app.App;
import me.rhys.uploader.util.Logger;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;

import java.io.File;

@SuppressWarnings("FieldCanBeLocal")
public class UploaderService {

    public void start(File file, String serverDirectory) {
        Logger.log("Connecting...");
        SSHClient sshClient = new SSHClient();
        sshClient.addHostKeyVerifier(new PromiscuousVerifier());

        try {
            sshClient.connect(
                    App.getInstance().getConfigValues().getFtpIP(),
                    App.getInstance().getConfigValues().getFtpPort()
            );

            Logger.log("Authenticating...");
            sshClient.authPassword(
                    App.getInstance().getConfigValues().getFtpUsername(),
                    App.getInstance().getConfigValues().getFtpPassword()
            );

            Logger.log("Connected to SFTP protocol!");

            SFTPClient sftpClient = sshClient.newSFTPClient();

            Logger.log("Uploading " + file.getAbsolutePath() + "...");

            sftpClient.put(file.getAbsolutePath(), serverDirectory + "/" + file.getName());

            Logger.log("Disconnecting...");

            sftpClient.close();
            sshClient.disconnect();

            Logger.log("Disconnected from server!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
