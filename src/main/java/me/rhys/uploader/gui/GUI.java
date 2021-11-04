package me.rhys.uploader.gui;

import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.settings.ThemeSettings;
import com.github.weisj.darklaf.theme.OneDarkTheme;
import lombok.Getter;
import me.rhys.uploader.app.App;
import me.rhys.uploader.util.Logger;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("SameParameterValue")
public class GUI {

    @Getter
    private JLabel statusLabel;

    private final FTPGui FTP = new FTPGui();

    public void openGUI() {
        SwingUtilities.invokeLater(() -> {
            LafManager.install(new OneDarkTheme());
            LafManager.install();

            JFrame jFrame = new JFrame("AutoUploader");
            jFrame.setSize(600, 175);
            jFrame.setLocationRelativeTo(null);
            jFrame.getContentPane().setLayout(null);

            JMenuBar jMenuBar = new JMenuBar();
            JMenu jMenu = new JMenu("Settings");

            JMenuItem themeItem = new JMenuItem("Theme");
            themeItem.addActionListener(e -> ThemeSettings.showSettingsDialog(jFrame));
            jMenu.add(themeItem);

            JMenuItem ftpSettings = new JMenuItem("FTP Settings");
            ftpSettings.addActionListener(e -> this.FTP.openGUI());
            jMenu.add(ftpSettings);

            jMenuBar.add(jMenu);
            jFrame.setJMenuBar(jMenuBar);

            JTextField serverDirectoryField;

            jFrame.getContentPane().add(this.addLabel("Server Directory", 255, 10));
            jFrame.getContentPane().add(serverDirectoryField = this.addTextArea("plugins",
                    240, 45, 120, 30));

            JButton startButton;
            jFrame.getContentPane().add(startButton = this.addButton("Start", 240, 100, 120, 30));

            jFrame.getContentPane().add(statusLabel = this.addLabel("Status: Waiting...", 3, 110));
            statusLabel.setForeground(Color.YELLOW);

            startButton.addActionListener(e -> {
                if (App.getInstance().getConfigValues().getFtpIP() == null || serverDirectoryField.getText() == null) {
                    statusLabel.setForeground(Color.RED);
                    statusLabel.setText("Status: not setup!");
                    return;
                }

                statusLabel.setForeground(Color.CYAN);
                statusLabel.setText("Status: initializing...");

                startButton.setEnabled(false);

                App.getInstance().getExecutorService().execute(() ->
                        App.getInstance().getFileWatcher().start(serverDirectoryField.getText()));
            });

            jFrame.setResizable(false);
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jFrame.setVisible(true);
        });
    }

    JLabel addLabel(String str, int x, int y) {
        JLabel jTextField = new JLabel(str);
        jTextField.setBounds(x, y, 120, 30);
        return jTextField;
    }

    JTextField addTextArea(String str, int x, int y, int width, int height) {
        JTextField jTextField = new JTextField(str);
        jTextField.setBounds(x, y, width, height);
        return jTextField;
    }

    JButton addButton(String str, int x, int y, int width, int height) {
        JButton jButton = new JButton(str);
        jButton.setBounds(x, y, width, height);
        return jButton;
    }
}
