package org.example.custamizingcrud.Services;

import org.example.custamizingcrud.Classes.CustomizeTour;
import java.io.*;
import java.util.*;

public class CustomizeTourBST {
    private static class Node {
        CustomizeTour data;
        Node left, right;

        Node(CustomizeTour data) {
            this.data = data;
        }
    }

    private Node root;
    private final String FILE_PATH = "C:/Users/CHAMIDU/OneDrive/Desktop/coustamization_tour_1/ProjectUsingSpring/CustamizingCrud/tours.txt";

    public CustomizeTourBST() {
        loadFromFile();
    }

    public void insert(CustomizeTour tour) {
        root = insertRecursive(root, tour);
        saveAllToFile();
    }

    private Node insertRecursive(Node current, CustomizeTour tour) {
        if (current == null) return new Node(tour);
        if (tour.getName().compareToIgnoreCase(current.data.getName()) < 0) {
            current.left = insertRecursive(current.left, tour);
        } else if (tour.getName().compareToIgnoreCase(current.data.getName()) > 0) {
            current.right = insertRecursive(current.right, tour);
        } else {
            current.data = tour;
        }
        return current;
    }

    public List<CustomizeTour> inOrderTraversal() {
        List<CustomizeTour> result = new ArrayList<>();
        inOrder(root, result);
        return result;
    }

    private void inOrder(Node node, List<CustomizeTour> list) {
        if (node != null) {
            inOrder(node.left, list);
            list.add(node.data);
            inOrder(node.right, list);
        }
    }

    public CustomizeTour searchByName(String name) {
        return searchRecursive(root, name);
    }

    private CustomizeTour searchRecursive(Node node, String name) {
        if (node == null) return null;
        if (name.equalsIgnoreCase(node.data.getName())) return node.data;
        if (name.compareToIgnoreCase(node.data.getName()) < 0)
            return searchRecursive(node.left, name);
        return searchRecursive(node.right, name);
    }

    public void deleteByName(String name) {
        root = deleteRecursive(root, name);
        saveAllToFile();
    }

    private Node deleteRecursive(Node node, String name) {
        if (node == null) return null;
        if (name.compareToIgnoreCase(node.data.getName()) < 0) {
            node.left = deleteRecursive(node.left, name);
        } else if (name.compareToIgnoreCase(node.data.getName()) > 0) {
            node.right = deleteRecursive(node.right, name);
        } else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            Node smallest = findMin(node.right);
            node.data = smallest.data;
            node.right = deleteRecursive(node.right, smallest.data.getName());
        }
        return node;
    }

    private Node findMin(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    private void saveAllToFile() {
        List<CustomizeTour> tours = inOrderTraversal();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (CustomizeTour tour : tours) {
                writer.write(formatTour(tour));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String formatTour(CustomizeTour tour) {
        return tour.getName() + "," +
                tour.getEmail() + "," +
                tour.getPhone() + "," +
                tour.getTitle() + "," +
                tour.getCountry() + "," +
                tour.getPickup() + "," +
                tour.getArrival_date() + "," +
                tour.getDeparture_date() + "," +
                tour.getDuration() + "," +
                tour.getVehicle() + "," +
                tour.getAdults() + "," +
                tour.getChildren() + "," +
                tour.getAccommodation() + "," +
                tour.getBudget() + "," +
                String.join("|", tour.getAge_group()) + "," +
                String.join("|", tour.getDestinations());
    }

    private void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 15) {
                    CustomizeTour tour = new CustomizeTour();
                    tour.setName(parts[0]);
                    tour.setEmail(parts[1]);
                    tour.setPhone(parts[2]);
                    tour.setTitle(parts[3]);
                    tour.setCountry(parts[4]);
                    tour.setPickup(parts[5]);
                    tour.setArrival_date(parts[6]);
                    tour.setDeparture_date(parts[7]);
                    tour.setDuration(Integer.parseInt(parts[8]));
                    tour.setVehicle(parts[9]);
                    tour.setAdults(Integer.parseInt(parts[10]));
                    tour.setChildren(Integer.parseInt(parts[11]));
                    tour.setAccommodation(parts[12]);
                    tour.setBudget(Double.parseDouble(parts[13]));
                    tour.setAge_group(Arrays.asList(parts[14].split("\\|")));
                    if (parts.length >= 16) {
                        tour.setDestinations(Arrays.asList(parts[15].split("\\|")));
                    } else {
                        tour.setDestinations(Collections.emptyList());
                    }
                    root = insertRecursive(root, tour);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}