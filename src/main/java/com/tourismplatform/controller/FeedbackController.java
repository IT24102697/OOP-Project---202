package com.tourismplatform.controller;

import com.tourismplatform.model.Feedback;
import com.tourismplatform.storage.FeedbackStorage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FeedbackController {

    private static int feedbackId = 1;

    // === USER ===

    @GetMapping("/feedback")
    public String feedbackForm() {
        return "feedback_form";
    }

    @PostMapping("/feedback")
    public String submitFeedback(@RequestParam String username,
                                 @RequestParam String comment,
                                 Model model) {
        Feedback fb = new Feedback(feedbackId++, username, comment);
        FeedbackStorage.save(fb);
        model.addAttribute("success", "Thank you for your feedback!");
        model.addAttribute("username", username); // Pass username back for view link
        return "feedback_form";
    }

    @GetMapping("/feedback/view/{username}")
    public String viewUserFeedbacks(@PathVariable String username, Model model) {
        List<Feedback> all = FeedbackStorage.loadAll();
        List<Feedback> userFeedbacks = new ArrayList<>();
        for (Feedback fb : all) {
            if (fb.getUsername().equalsIgnoreCase(username)) {
                userFeedbacks.add(fb);
            }
        }
        model.addAttribute("feedbacks", userFeedbacks);
        model.addAttribute("username", username);
        return "user_feedback_list";
    }

    @GetMapping("/feedback/edit/{id}")
    public String editUserFeedback(@PathVariable int id, Model model) {
        Feedback fb = FeedbackStorage.findById(id);
        model.addAttribute("feedback", fb);
        return "user_feedback_edit";
    }

    @PostMapping("/feedback/update")
    public String updateUserFeedback(@RequestParam int id,
                                     @RequestParam String username,
                                     @RequestParam String comment) {
        FeedbackStorage.update(new Feedback(id, username, comment));
        return "redirect:/feedback/view/" + username;
    }

    @PostMapping("/feedback/delete")
    public String deleteUserFeedback(@RequestParam int id,
                                     @RequestParam String username) {
        FeedbackStorage.delete(id);
        return "redirect:/feedback/view/" + username;
    }

    // === ADMIN ===

    @GetMapping("/admin/feedbacks")
    public String viewFeedbacks(Model model) {
        List<Feedback> list = FeedbackStorage.loadAll();
        model.addAttribute("feedbacks", list);
        return "admin_feedback_list";
    }

    @GetMapping("/admin/feedback/edit/{id}")
    public String editFeedback(@PathVariable int id, Model model) {
        Feedback fb = FeedbackStorage.findById(id);
        model.addAttribute("feedback", fb);
        return "admin_feedback_edit";
    }

    @PostMapping("/admin/feedback/update")
    public String updateFeedback(@RequestParam int id,
                                 @RequestParam String username,
                                 @RequestParam String comment) {
        FeedbackStorage.update(new Feedback(id, username, comment));
        return "redirect:/admin/feedbacks";
    }

    @PostMapping("/admin/feedback/delete")
    public String deleteFeedback(@RequestParam int id) {
        FeedbackStorage.delete(id);
        return "redirect:/admin/feedbacks";
    }
}
