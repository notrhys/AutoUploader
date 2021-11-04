package me.rhys.uploader.gui;

import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.OneDarkTheme;
import me.rhys.uploader.app.App;
import me.rhys.uploader.util.Logger;

import javax.swing.*;

@SuppressWarnings("SameParameterValue")
public class FTPGui {

    public void openGUI() {
        SwingUtilities.invokeLater(() -> {
            LafManager.install(new OneDarkTheme());
            LafManager.install();

            JFrame jFrame = new JFrame("FTP Settings");
            jFrame.setSize(600, 400);
            jFrame.setLocationRelativeTo(null);
            jFrame.getContentPane().setLayout(null);

            JTextField ipField, portField, userField, passwordField;

            jFrame.getContentPane().add(this.addLabel("IP", 295, 10));
            jFrame.getContentPane().add(ipField = this.addTextArea("127.0.0.1", 240, 40, 120, 30));

            jFrame.getContentPane().add(this.addLabel("Port", 287, 75));
            jFrame.getContentPane().add(portField = this.addTextArea("22", 240, 100, 120, 30));

            jFrame.getContentPane().add(this.addLabel("Username", 275, 130));
            jFrame.getContentPane().add(userField = this.addTextArea("root", 240, 160, 120, 30));

            jFrame.getContentPane().add(this.addLabel("Password", 275, 190));
            jFrame.getContentPane().add(passwordField = this.addTextArea("foobar", 240, 219, 120, 30));

            // too lazy to check for each value to be not null
            if (App.getInstance().getConfigValues().getFtpIP() != null) {
                ipField.setText(App.getInstance().getConfigValues().getFtpIP());
                portField.setText(String.valueOf(App.getInstance().getConfigValues().getFtpPort()));
                userField.setText(App.getInstance().getConfigValues().getFtpUsername());
                passwordField.setText(App.getInstance().getConfigValues().getFtpPassword());

                Logger.log("Auto filled fields from config.json");
            }

            JButton saveButton;
            jFrame.getContentPane().add(saveButton = this.addButton("Save", 240, 270, 120, 30));

            saveButton.addActionListener(e -> {
                App.getInstance().getConfigManager().setConnectionValues(
                        ipField.getText(),
                        Integer.parseInt(portField.getText()),
                        userField.getText(),
                        passwordField.getText()
                );

                App.getInstance().getConfigManager().saveConfig();
            });

            jFrame.setResizable(false);
            jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
