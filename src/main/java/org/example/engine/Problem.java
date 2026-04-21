package org.example.engine;

public class Problem {
    public int id;
    public String title;
    public String description;

    // The new variables that were causing the "Cannot resolve symbol" error
    public String templateCode;
    public int testInput;
    public int expectedOutput;

    // Updated Constructor to include the new fields
    public Problem(int id, String title, String description, String templateCode, int testInput, int expectedOutput) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.templateCode = templateCode;
        this.testInput = testInput;
        this.expectedOutput = expectedOutput;
    }
}