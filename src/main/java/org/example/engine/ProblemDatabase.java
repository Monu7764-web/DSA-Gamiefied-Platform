package org.example.engine;

import java.util.ArrayList;
import java.util.List;

public class ProblemDatabase {
    public static List<Problem> getProblems() {
        List<Problem> quests = new ArrayList<>();

        // --- LEVEL 1: THE GATEKEEPER (BUBBLE SORT) ---
        quests.add(new Problem(1, "Bubble Sort",
                "Quest: In Bubble Sort, the total comparisons for an array of size 'n' is (n*(n-1))/2. \n" +
                        "Calculate this for n to unlock the Neon Bubble Simulation!",
                "public class Solution {\n    public int solve(int n) {\n        // Quest: Return (n * (n - 1)) / 2\n        return 0;\n    }\n}",
                10, 45));

        // --- LEVEL 2: TARGET SEARCH LOGIC ---
        quests.add(new Problem(2, "Target Search Logic",
                "Quest: Your target 'X' is 100. Given input 'n', return the distance needed to reach the target (100 - n). \n" +
                        "(Unlocks the Binary Search Target Scanner!)",
                "public class Solution {\n    public int solve(int n) {\n        // Quest: Find distance to reach 100 (100 - n)\n        return 0;\n    }\n}",
                40, 60));

        // --- LEVEL 3: RECURSION MASTER ---
        quests.add(new Problem(3, "Factorial Path",
                "Quest: Find the factorial of n. Remember: 5! = 120.",
                "public class Solution {\n    public int solve(int n) {\n        // Quest: Write recursive logic for n!\n        return 0;\n    }\n}",
                5, 120));

        // --- LEVEL 4: DYNAMIC PROGRAMMING INTRO ---
        quests.add(new Problem(4, "Fibonacci Step",
                "Quest: Return the nth Fibonacci number (0, 1, 1, 2, 3, 5...).",
                "public class Solution {\n    public int solve(int n) {\n        // Quest: Calculate nth Fibonacci number\n        return 0;\n    }\n}",
                7, 13));

        // --- LEVEL 5: BITWISE MAGIC ---
        quests.add(new Problem(5, "Power of 2 Check",
                "Quest: Return 1 if n is power of 2, else 0. (Hint: n & (n-1))",
                "public class Solution {\n    public int solve(int n) {\n        // Quest: Power of 2 logic here\n        return -1;\n    }\n}",
                16, 1));

        // --- LEVEL 6: NUMBER THEORY ---
        quests.add(new Problem(6, "Digit Counter",
                "Quest: Count how many digits are in n.",
                "public class Solution {\n    public int solve(int n) {\n        return 0;\n    }\n}",
                5000, 4));

        // --- LEVEL 7: GEOMETRY ENGINE ---
        quests.add(new Problem(7, "Circle Area",
                "Quest: Given radius n, return area (3 * n * n).",
                "public class Solution {\n    public int solve(int n) {\n        return 0;\n    }\n}",
                5, 75));

        // --- LEVEL 8: BINARY CONVERSION ---
        quests.add(new Problem(8, "Set Bits",
                "Quest: Count number of 1s in binary of n.",
                "public class Solution {\n    public int solve(int n) {\n        return 0;\n    }\n}",
                7, 3));

        // --- LEVEL 9: REVERSE LOGIC ---
        quests.add(new Problem(9, "Reverse",
                "Quest: Reverse the digits of n.",
                "public class Solution {\n    public int solve(int n) {\n        return 0;\n    }\n}",
                123, 321));

        // --- LEVEL 10: MODULO ART ---
        quests.add(new Problem(10, "Modulo Art",
                "Quest: Find the last digit of n and return its square.",
                "public class Solution {\n    public int solve(int n) {\n        return 0;\n    }\n}",
                14, 16));

        // --- DYNAMIC LEVELS 11-50 ---
        for (int i = 11; i <= 50; i++) {
            quests.add(new Problem(i, "Legacy Challenge " + i,
                    "Advance through the rift! Return n + 100.",
                    "public class Solution {\n    public int solve(int n) {\n        return 0; // Return n + 100\n    }\n}",
                    i * 2, (i * 2) + 100));
        }

        return quests;
    }
}