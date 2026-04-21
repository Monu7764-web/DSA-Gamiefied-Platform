package org.example.ui;

import org.example.auth.AuthManager;
import javax.swing.*;
import java.awt.*;

public class LoginScreen extends JFrame {
    private JTextField userField;
    private JPasswordField passField;
    private AuthManager authManager;

    public LoginScreen() {
        authManager = new AuthManager(); // Initialize local storage handler

        setTitle("DSA Quest - Login");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(25, 25, 30));
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- Title ---
        JLabel title = new JLabel("PLAYER LOGIN", SwingConstants.CENTER);
        title.setFont(new Font("Monospaced", Font.BOLD, 28));
        title.setForeground(new Color(0, 255, 150));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(title, gbc);

        // --- Username ---
        JLabel uLabel = new JLabel("Username:");
        uLabel.setForeground(Color.GRAY);
        gbc.gridy = 1; gbc.gridwidth = 1;
        add(uLabel, gbc);

        userField = new JTextField(15);
        userField.setBackground(new Color(40, 40, 45));
        userField.setForeground(Color.WHITE);
        userField.setCaretColor(Color.WHITE);
        userField.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 70)));
        gbc.gridx = 1;
        add(userField, gbc);

        // --- Password ---
        JLabel pLabel = new JLabel("Password:");
        pLabel.setForeground(Color.GRAY);
        gbc.gridx = 0; gbc.gridy = 2;
        add(pLabel, gbc);

        passField = new JPasswordField(15);
        passField.setBackground(new Color(40, 40, 45));
        passField.setForeground(Color.WHITE);
        passField.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 70)));
        gbc.gridx = 1;
        add(passField, gbc);

        // --- Buttons Panel ---
        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        btnPanel.setOpaque(false);

        JButton loginBtn = createStyledButton("LOGIN", new Color(0, 150, 255));
        JButton signupBtn = createStyledButton("SIGN UP", new Color(100, 50, 200));

        btnPanel.add(loginBtn);
        btnPanel.add(signupBtn);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        add(btnPanel, gbc);

        // --- Action Listeners ---
        loginBtn.addActionListener(e -> handleLogin());
        signupBtn.addActionListener(e -> handleSignup());
    }

    private void handleLogin() {
        String user = userField.getText();
        String pass = new String(passField.getPassword());

        if (authManager.login(user, pass)) {
            new DashboardScreen(user).setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Credentials, Adventurer!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleSignup() {
        String user = userField.getText();
        String pass = new String(passField.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Provide a name and password!");
            return;
        }

        if (authManager.signup(user, pass)) {
            JOptionPane.showMessageDialog(this, "Profile Created! You may now login.");
        } else {
            JOptionPane.showMessageDialog(this, "User already exists!");
        }
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return btn;
    }
}
