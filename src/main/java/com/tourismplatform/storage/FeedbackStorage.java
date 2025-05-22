package com.tourismplatform.storage;

import com.tourismplatform.model.Feedback;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FeedbackStorage {
    private static final String FILE = "src/main/resources/data/feedback.txt";

    public static void save(Feedback fb) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE, true))) {
            writer.write(fb.getId() + "," + fb.getUsername() + "," + fb.getComment());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Feedback> loadAll() {
        List<Feedback> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 3);
                if (parts.length == 3) {
                    list.add(new Feedback(Integer.parseInt(parts[0]), parts[1], parts[2]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void update(Feedback updated) {
        List<Feedback> list = loadAll();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE))) {
            for (Feedback fb : list) {
                if (fb.getId() == updated.getId()) {
                    fb = updated;
                }
                writer.write(fb.getId() + "," + fb.getUsername() + "," + fb.getComment());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void delete(int id) {
        List<Feedback> list = loadAll();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE))) {
            for (Feedback fb : list) {
                if (fb.getId() != id) {
                    writer.write(fb.getId() + "," + fb.getUsername() + "," + fb.getComment());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Feedback findById(int id) {
        for (Feedback fb : loadAll()) {
            if (fb.getId() == id) return fb;
        }
        return null;
    }
}
