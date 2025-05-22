package com.tourismplatform.storage;

import com.tourismplatform.model.ContactMessage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ContactStorage {
    private static final String FILE = "src/main/resources/data/contact.txt";

    // Save new message
    public static void save(ContactMessage msg) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE, true))) {
            writer.write(msg.getId() + "," + msg.getName() + "," + msg.getEmail() + "," + msg.getMessage());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load all messages
    public static List<ContactMessage> loadAll() {
        List<ContactMessage> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 4);
                if (parts.length == 4) {
                    list.add(new ContactMessage(
                            Integer.parseInt(parts[0]), parts[1], parts[2], parts[3]
                    ));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Find a single message by ID
    public static ContactMessage findById(int id) {
        for (ContactMessage msg : loadAll()) {
            if (msg.getId() == id) return msg;
        }
        return null;
    }

    // Update an existing message
    public static void update(ContactMessage updated) {
        List<ContactMessage> list = loadAll();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE))) {
            for (ContactMessage msg : list) {
                if (msg.getId() == updated.getId()) {
                    msg = updated;
                }
                writer.write(msg.getId() + "," + msg.getName() + "," + msg.getEmail() + "," + msg.getMessage());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Delete a message by ID
    public static void delete(int id) {
        List<ContactMessage> list = loadAll();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE))) {
            for (ContactMessage msg : list) {
                if (msg.getId() != id) {
                    writer.write(msg.getId() + "," + msg.getName() + "," + msg.getEmail() + "," + msg.getMessage());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
