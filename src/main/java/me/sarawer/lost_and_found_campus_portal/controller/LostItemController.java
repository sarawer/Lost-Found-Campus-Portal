package me.sarawer.lost_and_found_campus_portal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import me.sarawer.lost_and_found_campus_portal.entity.LostItem;
import me.sarawer.lost_and_found_campus_portal.service.LostItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/lost")
public class LostItemController {
    private final LostItemService lostItemService;



    @GetMapping
    public String getAllLostItems(Model model) {
        model.addAttribute("lostItems", lostItemService.getAllLostItems());
        return "lost-items";
    }


    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("lostItem", new LostItem());
        return "lost-form";
    }


    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        LostItem item = lostItemService.getLostItem(id);

        if (item == null) {
            return "redirect:/lost";
        }

        model.addAttribute("lostItem", item);
        return "lost-form";
    }


    @PostMapping("/save")
    public String saveLostItem(@ModelAttribute LostItem lostItem) {
        if (lostItem.getId() == null) {
            lostItemService.createLostItem(lostItem);
        } else {
            lostItemService.updateLostItem(lostItem.getId(), lostItem);
        }
        return "redirect:/lost";
    }


    @GetMapping("/delete/{id}")
    public String deleteLostItem(@PathVariable Long id) {
        lostItemService.deleteLostItem(id);
        return "redirect:/lost";
    }
}
