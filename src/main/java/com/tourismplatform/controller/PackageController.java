package com.tourismplatform.controller;

import com.tourismplatform.model.TravelPackage;
import com.tourismplatform.storage.PackageStorage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PackageController {

    private static int packageId = 1;

    @GetMapping("/admin/packages")
    public String viewAdminPackages(Model model) {
        PackageStorage.loadFromFile();
        List<TravelPackage> packages = PackageStorage.inOrderTraversal();
        model.addAttribute("packages", packages);
        return "admin_package_list";
    }

    @GetMapping("/admin/package/add")
    public String showAddForm() {
        return "add_package";
    }

    @PostMapping("/admin/package/add")
    public String addPackage(@RequestParam String name,
                             @RequestParam String description,
                             @RequestParam double cost,
                             @RequestParam String imagePath) {
        TravelPackage tp = new TravelPackage(packageId++, name, description, cost, imagePath);
        PackageStorage.insert(tp);
        List<TravelPackage> all = PackageStorage.inOrderTraversal();
        PackageStorage.saveToFile(all);
        return "redirect:/admin/packages";
    }

    @GetMapping("/admin/package/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        PackageStorage.loadFromFile();
        List<TravelPackage> packages = PackageStorage.inOrderTraversal();
        for (TravelPackage tp : packages) {
            if (tp.getId() == id) {
                model.addAttribute("package", tp);
                break;
            }
        }
        return "edit_package";
    }

    @PostMapping("/admin/package/update")
    public String updatePackage(@RequestParam int id,
                                @RequestParam String name,
                                @RequestParam String description,
                                @RequestParam double cost,
                                @RequestParam String imagePath) {
        TravelPackage updated = new TravelPackage(id, name, description, cost, imagePath);
        PackageStorage.updatePackage(updated);
        return "redirect:/admin/packages";
    }

    @PostMapping("/admin/package/delete")
    public String deletePackage(@RequestParam int id) {
        PackageStorage.deleteById(id);
        return "redirect:/admin/packages";
    }

    @GetMapping("/packages")
    public String viewPackagesSorted(Model model) {
        PackageStorage.loadFromFile();
        List<TravelPackage> sorted = PackageStorage.getAllPackagesSortedByCost();
        model.addAttribute("packages", sorted);
        return "view_packages";
    }

    @PostMapping("/packages/book")
    public String bookPackage(@RequestParam int id,
                              @RequestParam String username,
                              Model model) {
        PackageStorage.loadFromFile();
        List<TravelPackage> list = PackageStorage.inOrderTraversal();
        for (TravelPackage pkg : list) {
            if (pkg.getId() == id) {
                PackageStorage.saveToBookingFile(pkg, username);
                model.addAttribute("message", "Booking Successful!!");
                return "booking_success";
            }
        }
        model.addAttribute("message", "Package not found.");
        return "booking_success";
    }
}