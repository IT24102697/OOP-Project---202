package com.tourismplatform.storage;

import com.tourismplatform.model.Admin;
import com.tourismplatform.model.Customer;
import com.tourismplatform.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserStorage {
    private static final String USER_FILE = "users.txt";
    private static final String ADMIN_FILE = "admin.txt";

    // ✅ Validate user credentials
    public static boolean validateUser(String username, String password, String role) {
        List<User> users = role.equals("admin") ? loadAdmins() : loadCustomers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    // ✅ Register a new customer
    public static void registerCustomer(Customer user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE, true))) {
            writer.write(user.getUsername() + "," + user.getPassword());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ✅ Load all customers from file
    public static List<User> loadCustomers() {
        return loadFromFile(USER_FILE, false);
    }

    // ✅ Load all admins from file
    public static List<User> loadAdmins() {
        return loadFromFile(ADMIN_FILE, true);
    }

    // 🔄 Internal shared method to load from file
    private static List<User> loadFromFile(String file, boolean isAdmin) {
        List<User> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length == 2) {
                    User u = isAdmin ? new Admin(parts[0], parts[1]) : new Customer(parts[0], parts[1]);
                    list.add(u);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ✅ Save entire customer list to file
    public static void saveAllCustomers(List<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE))) {
            for (User user : users) {
                writer.write(user.getUsername() + "," + user.getPassword());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ✅ Save entire admin list to file
    public static void saveAllAdmins(List<User> admins) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ADMIN_FILE))) {
            for (User admin : admins) {
                writer.write(admin.getUsername() + "," + admin.getPassword());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

