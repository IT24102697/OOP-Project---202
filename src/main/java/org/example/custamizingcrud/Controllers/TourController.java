package org.example.custamizingcrud.Controllers;

import org.example.custamizingcrud.Classes.CustomizeTour;
import org.example.custamizingcrud.Services.CustomizeTourBST;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TourController {

    private final CustomizeTourBST bst = new CustomizeTourBST();

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/addTour")
    public String showForm(Model model) {
        model.addAttribute("tour", new CustomizeTour());
        return "addTour";
    }

    @PostMapping("/submitCustomizeTour")
    public String submit(@ModelAttribute CustomizeTour tour) {
        bst.insert(tour);
        return "addingSuccess";
    }

    @GetMapping("/viewTours")
    public String viewTours(Model model) {
        model.addAttribute("tours", bst.inOrderTraversal());
        return "viewTours";
    }

    @GetMapping("/editTour")
    public String editForm(@RequestParam(name = "name", required = false) String name, Model model) {
        CustomizeTour tour = bst.searchByName(name != null ? name : "");
        if (tour != null) {
            model.addAttribute("tour", tour);
            return "editTour";
        } else {
            return "redirect:/viewTours";
        }
    }

    @PostMapping("/updateTour")
    public String update(@ModelAttribute CustomizeTour updatedTour) {
        bst.insert(updatedTour); // Replace the old entry if name is same
        return "redirect:/viewTours";
    }

    @GetMapping("/deleteTour")
    public String deleteTour(@RequestParam(name = "name") String name) {
        bst.deleteByName(name);
        return "redirect:/viewTours";
    }
}
