package org.example.simulations;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Random;

public class BubbleSortSim extends JFrame {
    private int[] array;
    private int currentIndex = 0;
    private int compareIndex = 0;
    private int swaps = 0;
    private int comparisons = 0;
    private boolean isSorted = false;
    private Timer timer;

    public BubbleSortSim() {
        setTitle("⚡ NEON BUBBLE SORT ENGINE ⚡");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        // Generate high-quality random data
        array = new int[16];
        Random rand = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = rand.nextInt(320) + 40;
        }

        JPanel renderPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawCinematicBars((Graphics2D) g);
            }
        };
        renderPanel.setBackground(new Color(10, 10, 15)); // Space Dark Background
        add(renderPanel);

        // --- HIGH-SPEED ANIMATION ENGINE ---
        timer = new Timer(80, e -> {
            if (!isSorted) {
                performAdvancedSortStep();
                renderPanel.repaint();
            } else {
                ((Timer) e.getSource()).stop();
            }
        });

        timer.start();
    }

    private void performAdvancedSortStep() {
        if (currentIndex < array.length) {
            if (compareIndex < array.length - currentIndex - 1) {
                comparisons++;
                if (array[compareIndex] > array[compareIndex + 1]) {
                    swaps++;
                    int temp = array[compareIndex];
                    array[compareIndex] = array[compareIndex + 1];
                    array[compareIndex + 1] = temp;
                }
                compareIndex++;
            } else {
                compareIndex = 0;
                currentIndex++;
            }
        } else {
            isSorted = true;
        }
    }

    private void drawCinematicBars(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int barWidth = 45;
        int gap = 8;
        int startX = (getWidth() - (array.length * (barWidth + gap))) / 2;

        // --- DRAW HUD (Heads Up Display) ---
        g.setFont(new Font("Orbitron", Font.BOLD, 14));
        g.setColor(new Color(0, 255, 200));
        g.drawString("ALGORITHM: BUBBLE SORT", 30, 40);
        g.setColor(Color.WHITE);
        g.drawString("COMPARISONS: " + comparisons, 30, 65);
        g.drawString("SWAPS: " + swaps, 30, 90);

        if(isSorted) {
            g.setColor(new Color(255, 215, 0)); // Gold
            g.drawString("STATUS: OPTIMIZED & SORTED", 30, 115);
        } else {
            g.setColor(new Color(255, 50, 50));
            g.drawString("STATUS: COMPUTING...", 30, 115);
        }

        for (int i = 0; i < array.length; i++) {
            int x = startX + (i * (barWidth + gap));
            int y = 500 - array[i];

            // --- NEON GRADIENT SYSTEM ---
            GradientPaint colorGradient;
            if (isSorted) {
                colorGradient = new GradientPaint(x, y, new Color(255, 215, 0), x, 500, new Color(150, 100, 0)); // Gold
            } else if (i == compareIndex || i == compareIndex + 1) {
                colorGradient = new GradientPaint(x, y, new Color(255, 50, 100), x, 500, new Color(100, 0, 50)); // Red Alert
            } else if (i >= array.length - currentIndex) {
                colorGradient = new GradientPaint(x, y, new Color(0, 200, 255), x, 500, new Color(0, 50, 150)); // Frozen Blue
            } else {
                colorGradient = new GradientPaint(x, y, new Color(70, 70, 90), x, 500, new Color(30, 30, 40)); // Idle Gray
            }

            // Draw Glow (Shadow effect)
            g.setColor(new Color(0, 0, 0, 100));
            g.fill(new RoundRectangle2D.Double(x + 4, y + 4, barWidth, array[i], 15, 15));

            // Draw Main Bar
            g.setPaint(colorGradient);
            g.fill(new RoundRectangle2D.Double(x, y, barWidth, array[i], 15, 15));

            // Draw Border for "Glassmorphism" effect
            g.setColor(new Color(255, 255, 255, 50));
            g.setStroke(new BasicStroke(1));
            g.draw(new RoundRectangle2D.Double(x, y, barWidth, array[i], 15, 15));

            // Draw Value
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 12));
            g.drawString(String.valueOf(array[i]), x + (barWidth/4), y - 10);
        }
    }
}