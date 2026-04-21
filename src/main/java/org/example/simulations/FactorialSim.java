package org.example.simulations;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

public class FactorialSim extends JFrame {
    private int n = 6; // Hum 6! tak dikhayenge visualization ke liye
    private List<String> stackTrace = new ArrayList<>();
    private int currentStep = 0;
    private boolean returning = false;
    private long result = 1;
    private Timer timer;

    public FactorialSim() {
        setTitle("🌀 RECURSIVE FACTORIAL ENGINE - NEURAL STACK");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel renderPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawRecursiveStack((Graphics2D) g);
            }
        };
        renderPanel.setBackground(new Color(10, 10, 25)); // Deep Space Blue
        add(renderPanel);

        // --- ANIMATION LOGIC ---
        timer = new Timer(1000, e -> {
            if (!returning) {
                if (currentStep <= n) {
                    stackTrace.add("fact(" + (n - currentStep) + ")");
                    currentStep++;
                } else {
                    returning = true;
                    currentStep--;
                }
            } else {
                if (currentStep > 0) {
                    currentStep--;
                    // Factorial calculation simulation
                    result *= (currentStep == 0 ? 1 : (n - currentStep + 1));
                } else {
                    ((Timer) e.getSource()).stop();
                }
            }
            renderPanel.repaint();
        });
        timer.start();
    }

    private void drawRecursiveStack(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int startY = 550;
        int boxHeight = 50;
        int boxWidth = 300;
        int x = (getWidth() - boxWidth) / 2;

        // --- TITLE ---
        g.setFont(new Font("Orbitron", Font.BOLD, 24));
        g.setColor(new Color(0, 200, 255));
        g.drawString("RECURSION STACK VISUALIZER", x - 50, 50);

        // --- DRAW STACK BLOCKS ---
        for (int i = 0; i < stackTrace.size(); i++) {
            int y = startY - (i * (boxHeight + 10));

            // Highlight current active call
            boolean isActive = (i == currentStep - 1 && !returning) || (i == currentStep && returning);

            if (isActive) {
                drawGlow(g, x, y, boxWidth, boxHeight, new Color(255, 0, 150)); // Neon Pink Glow
                g.setPaint(new GradientPaint(x, y, new Color(255, 50, 150), x + boxWidth, y, new Color(150, 0, 100)));
            } else {
                g.setPaint(new GradientPaint(x, y, new Color(50, 50, 100, 150), x + boxWidth, y, new Color(20, 20, 50, 150)));
                g.setColor(new Color(0, 255, 255, 100));
                g.drawRoundRect(x, y, boxWidth, boxHeight, 15, 15);
            }

            g.fill(new RoundRectangle2D.Double(x, y, boxWidth, boxHeight, 15, 15));

            // Text inside box
            g.setColor(Color.WHITE);
            g.setFont(new Font("Monospaced", Font.BOLD, 18));
            g.drawString(stackTrace.get(i), x + 20, y + 32);

            // Draw "Return" values when coming back
            if (returning && i >= currentStep) {
                g.setColor(new Color(0, 255, 100));
                g.setFont(new Font("Arial", Font.ITALIC, 14));
                g.drawString("✔ Resolved", x + boxWidth + 20, y + 30);
            }
        }

        // --- FINAL RESULT HUD ---
        if (returning && currentStep == 0) {
            g.setColor(new Color(0, 255, 150));
            g.setFont(new Font("Orbitron", Font.BOLD, 40));
            String resStr = n + "! = 720"; // 6! case
            g.drawString(resStr, (getWidth() - g.getFontMetrics().stringWidth(resStr))/2, 350);
        }
    }

    private void drawGlow(Graphics2D g, int x, int y, int w, int h, Color c) {
        for (int i = 1; i <= 10; i++) {
            g.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 80 / i));
            g.drawRoundRect(x - i, y - i, w + (i * 2), h + (i * 2), 15, 15);
        }
    }
}