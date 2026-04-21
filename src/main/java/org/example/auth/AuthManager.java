package org.example.auth;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class AuthManager {
    private static final String FILE_PATH = "users.dat";
    private Map<String, String> users = new HashMap<>(); // Username -> Password

    public AuthManager() {
        loadUsers();
    }

    public boolean signup(String username, String password) {
        if (users.containsKey(username)) return false;
        users.put(username, password);
        saveUsers();
        return true;
    }

    public boolean login(String username, String password) {
        return password.equals(users.get(username));
    }

    private void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            users = (Map<String, String>) ois.readObject();
        } catch (Exception e) {
            // File might not exist yet, which is fine
        }
    }
}
