package org.example.simulations;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Random;

public class TargetSearchSim extends JFrame {
    private int[] array;
    private int target;
    private int low, high, mid = -1;
    private boolean found = false;
    private Timer timer;
    private float scanLineY = 0;
    private int stepCounter = 0; // FIXED: Yahan class level par rakha hai counter ko

    public TargetSearchSim() {
        setTitle("🎯 APEX TARGET SCANNER - OS v2.0");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Sorted array generation
        array = new int[20];
        Random rand = new Random();
        int start = 20;
        for (int i = 0; i < array.length; i++) {
            start += rand.nextInt(20) + 10;
            array[i] = start;
        }

        target = array[rand.nextInt(array.length)];
        low = 0;
        high = array.length - 1;

        JPanel renderPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawAdvancedUI((Graphics2D) g);
            }
        };
        renderPanel.setBackground(new Color(5, 8, 15));
        add(renderPanel);

        // --- THE ENGINE ---
        timer = new Timer(30, e -> { // 30ms for smoother animation
            // 1. Update Scan Line
            scanLineY = (scanLineY + 6) % getHeight();

            // 2. Binary Search Logic (Logic speed control)
            stepCounter++;
            if (stepCounter % 30 == 0) { // Har 30 frames baad search ka ek step lega
                if (low <= high && !found) {
                    mid = low + (high - low) / 2;
                    if (array[mid] == target) {
                        found = true;
                    } else if (array[mid] < target) {
                        low = mid + 1;
                    } else {
                        high = mid - 1;
                    }
                }
            }
            renderPanel.repaint();
        });
        timer.start();
    }

    private void drawAdvancedUI(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Grid Background
        g.setColor(new Color(0, 255, 255, 15));
        for (int i = 0; i < getWidth(); i += 50) g.drawLine(i, 0, i, getHeight());
        for (int i = 0; i < getHeight(); i += 50) g.drawLine(0, i, getWidth(), i);

        // Scan Line
        g.setPaint(new GradientPaint(0, scanLineY - 60, new Color(0, 255, 255, 0), 0, scanLineY, new Color(0, 255, 255, 60)));
        g.fillRect(0, (int)scanLineY - 60, getWidth(), 60);
        g.setColor(new Color(0, 255, 255, 150));
        g.drawLine(0, (int)scanLineY, getWidth(), (int)scanLineY);

        int barWidth = 38;
        int gap = 8;
        int totalWidth = array.length * (barWidth + gap);
        int startX = (getWidth() - totalWidth) / 2;

        // Top HUD
        g.setColor(new Color(20, 30, 50, 220));
        g.fillRoundRect(startX, 30, totalWidth, 80, 15, 15);
        g.setColor(new Color(0, 255, 255, 120));
        g.drawRoundRect(startX, 30, totalWidth, 80, 15, 15);

        g.setFont(new Font("Orbitron", Font.BOLD, 22));
        g.setColor(new Color(0, 255, 255));
        g.drawString("CORE SCANNING: " + target, startX + 25, 65);

        g.setFont(new Font("Monospaced", Font.BOLD, 14));
        g.setColor(new Color(180, 180, 180));
        g.drawString("L:" + low + " | M:" + mid + " | H:" + high, startX + 25, 90);

        // Drawing Bars
        for (int i = 0; i < array.length; i++) {
            int x = startX + (i * (barWidth + gap));
            int barHeight = array[i] + 70;
            int y = 550 - barHeight;

            Color themeColor;
            if (found && i == mid) {
                themeColor = new Color(0, 255, 150);
                drawGlow(g, x, y, barWidth, barHeight, themeColor);
            } else if (i == mid) {
                themeColor = new Color(255, 200, 0);
                drawGlow(g, x, y, barWidth, barHeight, themeColor);
            } else if (i >= low && i <= high) {
                themeColor = new Color(0, 180, 255);
            } else {
                themeColor = new Color(40, 45, 60);
            }

            GradientPaint gp = new GradientPaint(x, y, themeColor, x, 550, themeColor.darker().darker());
            g.setPaint(gp);
            g.fill(new RoundRectangle2D.Double(x, y, barWidth, barHeight, 10, 10));

            // Values
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 11));
            g.drawString(String.valueOf(array[i]), x + 8, y - 10);
        }

        // Final Result
        if (found) {
            g.setColor(new Color(5, 10, 20, 200));
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(new Color(0, 255, 150));
            g.setFont(new Font("Orbitron", Font.BOLD, 60));
            String msg = "TARGET LOCKED";
            g.drawString(msg, (getWidth() - g.getFontMetrics().stringWidth(msg))/2, getHeight()/2);
        }
    }

    private void drawGlow(Graphics2D g, int x, int y, int w, int h, Color c) {
        for (int i = 1; i <= 10; i++) {
            g.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 70 / i));
            g.drawRoundRect(x - i, y - i, w + (i * 2), h + (i * 2), 10, 10);
        }
    }
}