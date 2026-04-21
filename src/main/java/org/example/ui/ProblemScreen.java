package org.example.ui;

import org.example.engine.Problem;
import org.example.simulations.FactorialSim;
import org.example.simulations.LevelUpAnimation;
import org.example.simulations.BubbleSortSim;
import org.example.simulations.TargetSearchSim;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.tools.*;
import java.awt.*;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.List;

public class ProblemScreen extends JFrame {
    private Problem problem;
    private JTextArea codeEditor;
    private JTextArea consoleOutput;
    private JButton runBtn;
    private JButton simBtn;

    public ProblemScreen(Problem problem) {
        this.problem = problem;

        // --- Frame Settings ---
        setTitle("DSA Quest: " + problem.title);
        setSize(1100, 850);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        // --- 1. Header (Instructions) ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(245, 245, 250));
        headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(200, 200, 210)));

        JLabel titleLabel = new JLabel("  🎯 " + problem.title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(30, 30, 50));

        JTextArea descArea = new JTextArea(problem.description);
        descArea.setEditable(false);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setBackground(new Color(245, 245, 250));
        descArea.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        descArea.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));

        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(descArea, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        // --- 2. Code Editor ---
        codeEditor = new JTextArea();
        codeEditor.setText(problem.templateCode);
        codeEditor.setFont(new Font("Consolas", Font.PLAIN, 20));
        codeEditor.setTabSize(4);
        codeEditor.setMargin(new Insets(15, 15, 15, 15));
        codeEditor.setCaretColor(Color.RED);

        JScrollPane editorScroll = new JScrollPane(codeEditor);
        TitledBorder editorBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 200), 2), " 💻 SOURCE CODE ");
        editorBorder.setTitleFont(new Font("Segoe UI", Font.BOLD, 14));
        editorScroll.setBorder(editorBorder);

        // --- 3. Dynamic Console System ---
        consoleOutput = new JTextArea();
        consoleOutput.setEditable(false);
        consoleOutput.setBackground(new Color(15, 15, 20));
        consoleOutput.setForeground(new Color(0, 255, 150));
        consoleOutput.setFont(new Font("Monospaced", Font.BOLD, 16));
        consoleOutput.setMargin(new Insets(10, 10, 10, 10));
        consoleOutput.setText(">> SYSTEM INITIALIZED.\n>> Waiting for code execution...");

        JScrollPane consoleScroll = new JScrollPane(consoleOutput);
        TitledBorder consoleBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 2), " 🖥️ LIVE TERMINAL ",
                TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 14), Color.BLACK);
        consoleScroll.setBorder(consoleBorder);

        // --- 4. Advanced Split Pane Layout ---
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, editorScroll, consoleScroll);
        splitPane.setDividerLocation(450);
        splitPane.setDividerSize(10);
        splitPane.setBorder(BorderFactory.createEmptyBorder(10, 15, 5, 15));
        add(splitPane, BorderLayout.CENTER);

        // --- 5. Footer (Buttons) ---
        JPanel buttonArea = new JPanel(new FlowLayout(FlowLayout.RIGHT, 25, 15));
        buttonArea.setBackground(new Color(230, 230, 240));
        buttonArea.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.LIGHT_GRAY));

        runBtn = new JButton("▶ RUN CODE");
        styleButton(runBtn, new Color(220, 20, 60));

        simBtn = new JButton("🎬 VIEW SIMULATION");
        styleButton(simBtn, new Color(0, 200, 80));

        // --- FIXED: Hamesha true rahega ---
        simBtn.setEnabled(true);

        buttonArea.add(runBtn);
        buttonArea.add(simBtn);
        add(buttonArea, BorderLayout.SOUTH);

        runBtn.addActionListener(e -> executeCodeDynamically());
        simBtn.addActionListener(e -> launchSimulation());
    }

    private void styleButton(JButton btn, Color bg) {
        btn.setPreferredSize(new Dimension(220, 55));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createRaisedBevelBorder());
    }

    private void executeCodeDynamically() {
        String code = codeEditor.getText();
        runBtn.setEnabled(false);
        consoleOutput.setText(">> ⚙️ COMPILING IN BACKGROUND...\n");

        SwingWorker<Void, String> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                String className = "Solution";
                File sourceFile = new File(className + ".java");

                try {
                    Files.write(sourceFile.toPath(), code.getBytes());
                    JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

                    if (compiler == null) {
                        publish("!! ❌ ERROR: JDK missing. Run with JDK, not JRE.\n");
                        return null;
                    }

                    ByteArrayOutputStream errStream = new ByteArrayOutputStream();
                    int result = compiler.run(null, null, errStream, sourceFile.getPath());

                    if (result == 0) {
                        publish(">> ✅ COMPILATION SUCCESS!\n");
                        publish(">> 🚀 Running Test Cases...\n");

                        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{new File(".").toURI().toURL()});
                        Class<?> cls = Class.forName(className, true, classLoader);
                        Object instance = cls.getDeclaredConstructor().newInstance();
                        Method method = cls.getDeclaredMethod("solve", int.class);

                        // Capture console output from user's code
                        PrintStream originalOut = System.out;
                        ByteArrayOutputStream customOutStream = new ByteArrayOutputStream();
                        System.setOut(new PrintStream(customOutStream));

                        int input = problem.testInput;
                        int expected = problem.expectedOutput;
                        Object actualObj = method.invoke(instance, input);
                        int actual = (actualObj instanceof Integer) ? (int) actualObj : -1;

                        System.setOut(originalOut);

                        publish("--------------------------------------------------\n");
                        publish("🧪 TEST DATA  -> Input: " + input + " | Expected: " + expected + "\n");
                        publish("🎯 YOUR RESULT -> " + actual + "\n");

                        if (actual == expected) {
                            publish("\n>> 🎉 WAH BHAI! PERFECT SOLUTION! 🎉\n");
                            publish(">> [MISSION ACCOMPLISHED]\n");
                        } else {
                            publish("\n>> ❌ OPPS! Answer galat hai.\n");
                            publish(">> Logic check karo bhai! Expected " + expected + " but got " + actual + ".\n");
                        }
                    } else {
                        publish(">> ❌ COMPILATION FAILED:\n" + errStream.toString() + "\n");
                    }
                    sourceFile.delete();
                    new File(className + ".class").delete();
                } catch (Exception ex) {
                    publish("!! 🚨 RUNTIME ERROR: " + (ex.getCause() != null ? ex.getCause() : ex.getMessage()) + "\n");
                }
                return null;
            }

            @Override
            protected void process(List<String> chunks) {
                for (String text : chunks) {
                    consoleOutput.append(text);
                }
                consoleOutput.setCaretPosition(consoleOutput.getDocument().getLength());
            }

            @Override
            protected void done() {
                runBtn.setEnabled(true);
            }
        };
        worker.execute();
    }

    private void launchSimulation() {
        String title = problem.title.toLowerCase();
        if (title.contains("bubble")) {
            new BubbleSortSim().setVisible(true);
        } else if (title.contains("target") || title.contains("two sum") || title.contains("search")) {
            new TargetSearchSim().setVisible(true);
        }
        else if (title.contains("factorial")) { // <--- YE WALA ADD KARIYE
            new FactorialSim().setVisible(true);
        }
        else {
            new LevelUpAnimation(problem.title).setVisible(true);
        }
    }
}