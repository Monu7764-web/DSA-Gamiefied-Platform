package org.example.ui;



import org.example.engine.Problem;
import org.example.engine.ProblemDatabase;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class DashboardScreen extends JFrame {

    public DashboardScreen(String username) {
        // 1. Basic Frame Setup
        setTitle("DSA Gamified - Hero: " + username);
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(18, 18, 22)); // Deep Dark Background
        setLayout(new BorderLayout());

        // 2. Header Panel (The "Status Bar")
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(30, 30, 40));
        header.setPreferredSize(new Dimension(1000, 100));
        header.setBorder(new EmptyBorder(10, 30, 10, 30));

        JLabel titleLabel = new JLabel("QUEST BOARD");
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 36));
        titleLabel.setForeground(new Color(0, 255, 150)); // Neon Cyan/Green

        JLabel userLabel = new JLabel("Player: " + username + " | Rank: Novice");
        userLabel.setFont(new Font("SansSerif", Font.ITALIC, 16));
        userLabel.setForeground(Color.LIGHT_GRAY);

        header.add(titleLabel, BorderLayout.WEST);
        header.add(userLabel, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // 3. The Grid Panel (The Quests)
        JPanel gridPanel = new JPanel(new GridLayout(0, 3, 20, 20)); // 3 Columns, infinite rows
        gridPanel.setBackground(new Color(18, 18, 22));
        gridPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        // Get problems from Database
        List<Problem> allProblems = ProblemDatabase.getProblems();

        for (Problem p : allProblems) {
            gridPanel.add(createQuestButton(p));
        }

        // Add ScrollPane in case you add 50+ problems
        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JButton createQuestButton(Problem p) {
        // Design a "Card" style button
        JButton btn = new JButton("<html><center>LEVEL " + p.id + "<br><font size='5'>" + p.title + "</font></center></html>");

        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(45, 45, 60));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 255), 2));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover Effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(70, 70, 100));
                btn.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 150), 2));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(45, 45, 60));
                btn.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 255), 2));
            }
        });

        // Click Logic
        btn.addActionListener(e -> {
            // Open the Problem Screen for this specific quest
            ProblemScreen ps = new ProblemScreen(p);
            ps.setVisible(true);
        });

        return btn;
    }
}
