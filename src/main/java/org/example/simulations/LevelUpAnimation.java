package org.example.simulations;

import javax.swing.*;
import java.awt.*;

public class LevelUpAnimation extends JFrame {
    private int yPosition = 400;
    private int alpha = 0;
    private String questName;

    public LevelUpAnimation(String questName) {
        this.questName = questName;
        setTitle("Quest Cleared!");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        AnimationPanel panel = new AnimationPanel();
        add(panel);

        // The Simulation Loop
        Timer timer = new Timer(30, e -> {
            if (yPosition > 250) {
                yPosition -= 2; // Move text up
            }
            if (alpha < 250) {
                alpha += 5; // Fade in
            }
            panel.repaint();
        });
        timer.start();
    }

    private class AnimationPanel extends JPanel {
        public AnimationPanel() {
            setBackground(new Color(20, 20, 25)); // Dark background
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw glowing "QUEST CLEARED" text
            g2d.setFont(new Font("Monospaced", Font.BOLD, 48));
            g2d.setColor(new Color(50, 255, 50, alpha)); // Neon green with fade-in

            String msg = "QUEST CLEARED!";
            FontMetrics fm = g2d.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(msg)) / 2;
            g2d.drawString(msg, x, yPosition);

            // Draw Subtext
            g2d.setFont(new Font("Arial", Font.PLAIN, 24));
            g2d.setColor(new Color(200, 200, 200, Math.max(0, alpha - 100)));
            String subMsg = "Mastered: " + questName;
            int subX = (getWidth() - g2d.getFontMetrics().stringWidth(subMsg)) / 2;
            g2d.drawString(subMsg, subX, yPosition + 50);
        }
    }
}
