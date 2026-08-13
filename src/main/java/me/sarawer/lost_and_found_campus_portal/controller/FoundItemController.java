package me.sarawer.lost_and_found_campus_portal.controller;

import lombok.RequiredArgsConstructor;
import me.sarawer.lost_and_found_campus_portal.entity.FoundItem;
import me.sarawer.lost_and_found_campus_portal.service.FoundItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("found")
public class FoundItemController {
    private final FoundItemService foundItemService;

    @GetMapping
    public String getAllFoundItems(Model model) {
        model.addAttribute("foundItems", foundItemService.getAllFoundItems());
        return "found-items";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("foundItem", new FoundItem());
        return "found-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        FoundItem item = foundItemService.getFoundItem(id);

        if (item == null) {
            return "redirect:/found";
        }

        model.addAttribute("foundItem", item);
        return "found-form";
    }

    @PostMapping("/save")
    public String saveFoundItem(@ModelAttribute FoundItem foundItem) {
        if (foundItem.getId() == null) {
            foundItemService.createFoundItem(foundItem);
        } else {
            foundItemService.updateFoundItem(foundItem.getId(), foundItem);
        }
        return "redirect:/found";
    }

    @GetMapping("/delete/{id}")
    public String deleteFoundItem(@PathVariable Long id) {
        foundItemService.deleteFoundItem(id);
        return "redirect:/found";
    }
}
