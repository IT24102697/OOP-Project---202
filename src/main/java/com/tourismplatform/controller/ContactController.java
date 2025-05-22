package com.tourismplatform.controller;

import com.tourismplatform.model.ContactMessage;
import com.tourismplatform.storage.ContactStorage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ContactController {

    private static int messageId = 1;

    // === USER ===

    @GetMapping("/contact")
    public String contactForm() {
        return "contact_form";
    }

    @PostMapping("/contact")
    public String submitContact(@RequestParam String name,
                                @RequestParam String email,
                                @RequestParam String message,
                                Model model) {
        ContactMessage msg = new ContactMessage(messageId++, name, email, message);
        ContactStorage.save(msg);
        model.addAttribute("success", "Message sent successfully!");
        model.addAttribute("name", name);  // for user view link
        return "contact_form";
    }

    @GetMapping("/contact/view/{name}")
    public String viewUserContacts(@PathVariable String name, Model model) {
        List<ContactMessage> list = ContactStorage.loadAll();
        List<ContactMessage> userMessages = new ArrayList<>();

        for (ContactMessage msg : list) {
            if (msg.getName().equalsIgnoreCase(name)) {
                userMessages.add(msg);
            }
        }

        model.addAttribute("messages", userMessages);
        model.addAttribute("name", name);
        return "user_contact_list";
    }

    @GetMapping("/contact/edit/{id}")
    public String editContactForm(@PathVariable int id, Model model) {
        ContactMessage msg = ContactStorage.findById(id);
        model.addAttribute("message", msg);
        return "user_contact_edit";
    }

    @PostMapping("/contact/update")
    public String updateContact(@RequestParam int id,
                                @RequestParam String name,
                                @RequestParam String email,
                                @RequestParam String message) {
        ContactMessage updated = new ContactMessage(id, name, email, message);
        ContactStorage.update(updated);
        return "redirect:/contact/view/" + name;
    }

    @PostMapping("/contact/delete")
    public String deleteContact(@RequestParam int id,
                                @RequestParam String name) {
        ContactStorage.delete(id);
        return "redirect:/contact/view/" + name;
    }

    // === ADMIN ===

    @GetMapping("/admin/contacts")
    public String viewMessages(Model model) {
        List<ContactMessage> messages = ContactStorage.loadAll();
        model.addAttribute("messages", messages);
        return "admin_contact_list";
    }

    @GetMapping("/admin/contact/edit/{id}")
    public String adminEditContact(@PathVariable int id, Model model) {
        ContactMessage msg = ContactStorage.findById(id);
        model.addAttribute("message", msg);
        return "admin_contact_edit";
    }

    @PostMapping("/admin/contact/update")
    public String adminUpdateContact(@RequestParam int id,
                                     @RequestParam String name,
                                     @RequestParam String email,
                                     @RequestParam String message) {
        ContactMessage updated = new ContactMessage(id, name, email, message);
        ContactStorage.update(updated);
        return "redirect:/admin/contacts";
    }

    @PostMapping("/admin/contact/delete")
    public String adminDeleteContact(@RequestParam int id) {
        ContactStorage.delete(id);
        return "redirect:/admin/contacts";
    }
}
