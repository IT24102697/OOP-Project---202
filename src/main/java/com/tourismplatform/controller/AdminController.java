package com.tourismplatform.controller;

import com.tourismplatform.model.User;
import com.tourismplatform.storage.UserStorage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/dashboard")
    public String adminDashboard(@RequestParam(required = false) String username, Model model) {
        model.addAttribute("username", username);
        return "admin_dashboard";
    }

    @GetMapping("/update")
    public String updateForm() {
        return "admin_update";
    }

    @PostMapping("/update")
    public String updatePassword(@RequestParam String username,
                                 @RequestParam String oldPassword,
                                 @RequestParam String newPassword,
                                 Model model) {
        List<User> admins = UserStorage.loadAdmins();
        boolean updated = false;

        for (User admin : admins) {
            if (admin.getUsername().equals(username) && admin.getPassword().equals(oldPassword)) {
                admin.setPassword(newPassword);
                updated = true;
                break;
            }
        }

        if (updated) {
            UserStorage.saveAllAdmins(admins);
            return "redirect:/admin-login";
        }

        model.addAttribute("error", "Invalid credentials");
        return "admin_update";
    }

    @GetMapping("/delete")
    public String deleteForm() {
        return "admin_delete";
    }

    @PostMapping("/delete")
    public String deleteAdmin(@RequestParam String username,
                              @RequestParam String password,
                              Model model) {
        List<User> admins = UserStorage.loadAdmins();
        boolean found = admins.removeIf(admin -> admin.getUsername().equals(username) && admin.getPassword().equals(password));

        if (found) {
            UserStorage.saveAllAdmins(admins);
            return "redirect:/admin-login";
        }

        model.addAttribute("error", "Admin not found or wrong credentials");
        return "admin_delete";
    }
}
