

package org.example; // This MUST match your folder structure in IntelliJ

import org.example.ui.LoginScreen; // We update the imports to use org.example
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // 1. Global Dark Theme Configuration
        try {
            UIManager.put("Panel.background", new Color(18, 18, 22));
            UIManager.put("Label.foreground", new Color(220, 220, 220));
            UIManager.put("Button.background", new Color(45, 45, 60));
            UIManager.put("Button.foreground", Color.WHITE);
            UIManager.put("TextField.background", new Color(40, 40, 45));
            UIManager.put("TextField.foreground", Color.WHITE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. Launching the Quest
        SwingUtilities.invokeLater(() -> {
            // Ensure LoginScreen is also in org.example.ui package
            new LoginScreen().setVisible(true);
        });
    }
}