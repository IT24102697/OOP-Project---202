package com.tourismplatform.storage;

import com.tourismplatform.model.TravelPackage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PackageStorage {
    private static final String FILE = "src/main/resources/data/packages.txt";

    // BST Node
    static class Node {
        TravelPackage data;
        Node left, right;

        Node(TravelPackage data) {
            this.data = data;
        }
    }

    private static Node root;

    public static void insert(TravelPackage tp) {
        root = insertRec(root, tp);
    }

    private static Node insertRec(Node root, TravelPackage tp) {
        if (root == null) return new Node(tp);
        if (tp.getId() < root.data.getId()) root.left = insertRec(root.left, tp);
        else root.right = insertRec(root.right, tp);
        return root;
    }

    public static List<TravelPackage> inOrderTraversal() {
        List<TravelPackage> list = new ArrayList<>();
        inOrderRec(root, list);
        return list;
    }

    private static void inOrderRec(Node node, List<TravelPackage> list) {
        if (node != null) {
            inOrderRec(node.left, list);
            list.add(node.data);
            inOrderRec(node.right, list);
        }
    }

    public static void loadFromFile() {
        root = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] p = line.split(",", 5);
                if (p.length == 5) {
                    int id = Integer.parseInt(p[0]);
                    String name = p[1];
                    String description = p[2];
                    double cost = Double.parseDouble(p[3]);
                    String image = p[4];
                    insert(new TravelPackage(id, name, description, cost, image));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveToFile(List<TravelPackage> packages) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE))) {
            for (TravelPackage tp : packages) {
                writer.write(tp.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void quickSortByCost(List<TravelPackage> list, int low, int high) {
        if (low < high) {
            int pi = partition(list, low, high);
            quickSortByCost(list, low, pi - 1);
            quickSortByCost(list, pi + 1, high);
        }
    }

    private static int partition(List<TravelPackage> list, int low, int high) {
        double pivot = list.get(high).getCost();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (list.get(j).getCost() <= pivot) {
                TravelPackage temp = list.get(++i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
        }
        TravelPackage temp = list.get(i + 1);
        list.set(i + 1, list.get(high));
        list.set(high, temp);
        return i + 1;
    }

    public static List<TravelPackage> getAllPackagesSortedByCost() {
        List<TravelPackage> list = inOrderTraversal();
        quickSortByCost(list, 0, list.size() - 1);
        return list;
    }

    public static void deleteById(int id) {
        List<TravelPackage> list = inOrderTraversal();
        list.removeIf(p -> p.getId() == id);
        saveToFile(list);
        loadFromFile();
    }

    public static void updatePackage(TravelPackage updated) {
        List<TravelPackage> list = inOrderTraversal();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == updated.getId()) {
                list.set(i, updated);
                break;
            }
        }
        saveToFile(list);
        loadFromFile();
    }

    public static void saveToBookingFile(TravelPackage tp, String username) {
        String bookingFile = "src/main/resources/data/booking.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(bookingFile, true))) {
            writer.write("BOOKING - User: " + username + " -> " + tp.toString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}