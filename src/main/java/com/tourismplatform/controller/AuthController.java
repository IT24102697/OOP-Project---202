package com.tourismplatform.controller;

import com.tourismplatform.model.Customer;
import com.tourismplatform.model.User;
import com.tourismplatform.storage.UserStorage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AuthController {

    // USER LOGIN
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String userLogin(@RequestParam String email,
                            @RequestParam String password,
                            Model model) {
        if (UserStorage.validateUser(email, password, "customer")) {
            model.addAttribute("username", email); // this is actually the email
            return "user_dashboard";
        }
        model.addAttribute("error", "Invalid credentials");
        return "login";
    }

    // USER REGISTRATION
    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               Model model) {
        UserStorage.registerCustomer(new Customer(username, password));
        return "redirect:/login";
    }

    // USER UPDATE PASSWORD
    @GetMapping("/user/update")
    public String updateUserForm() {
        return "user_update";
    }

    @PostMapping("/user/update")
    public String updateUserPassword(@RequestParam String username,
                                     @RequestParam String oldPassword,
                                     @RequestParam String newPassword,
                                     Model model) {
        List<User> users = UserStorage.loadCustomers();
        boolean updated = false;

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(oldPassword)) {
                user.setPassword(newPassword);
                updated = true;
                break;
            }
        }

        if (updated) {
            UserStorage.saveAllCustomers(users);
            return "redirect:/login";
        }

        model.addAttribute("error", "Invalid credentials");
        return "user_update";
    }

    // USER DELETE ACCOUNT
    @GetMapping("/user/delete")
    public String deleteUserForm() {
        return "user_delete";
    }

    @PostMapping("/user/delete")
    public String deleteUser(@RequestParam String username,
                             @RequestParam String password,
                             Model model) {
        List<User> users = UserStorage.loadCustomers();
        boolean removed = users.removeIf(user -> user.getUsername().equals(username) && user.getPassword().equals(password));

        if (removed) {
            UserStorage.saveAllCustomers(users);
            return "redirect:/register";
        }

        model.addAttribute("error", "User not found or incorrect password");
        return "user_delete";
    }
    @GetMapping("/admin-login")
    public String showAdminLoginForm() {
        return "admin_login"; // return your login form HTML
    }

    @PostMapping("/admin-login")
    public String adminLogin(@RequestParam String username,
                             @RequestParam String password,
                             Model model) {
        if (UserStorage.validateUser(username, password, "admin")) {
            model.addAttribute("username", username);
            return "admin_dashboard"; // Must match file name
        }
        model.addAttribute("error", "Invalid admin credentials");
        return "admin_login";
    }

}
