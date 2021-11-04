package me.rhys.uploader.app.protocol;

import me.rhys.uploader.util.Logger;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;

import java.io.File;

@SuppressWarnings("FieldCanBeLocal")
public class SFTPProtocol {

    private final String IP = "";
    private final int PORT = 22;
    private final String USERNAME = "root";
    private final String PASSWORD = "foobar";

    public void start(File file) {
        Logger.log("Connecting...");
        SSHClient sshClient = new SSHClient();
        sshClient.addHostKeyVerifier(new PromiscuousVerifier());

        try {
            sshClient.connect(this.IP, this.PORT);

            Logger.log("Authenticating...");
            sshClient.authPassword(this.USERNAME, this.PASSWORD);

            Logger.log("Connected to SFTP protocol!");

            SFTPClient sftpClient = sshClient.newSFTPClient();

            Logger.log("Uploading " + file.getAbsolutePath() + "...");

            sftpClient.put(file.getAbsolutePath(), "plugins/" + file.getName());

            Logger.log("Disconnecting...");

            sftpClient.close();
            sshClient.disconnect();

            Logger.log("Disconnected from server!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
