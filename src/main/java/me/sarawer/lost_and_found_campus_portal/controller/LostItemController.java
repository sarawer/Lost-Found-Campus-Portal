package me.sarawer.lost_and_found_campus_portal.controller;

import me.sarawer.lost_and_found_campus_portal.entity.LostItem;
import me.sarawer.lost_and_found_campus_portal.service.LostItemService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/lost")
public class LostItemController {

    private LostItemService lostItemService;

    public LostItemController(LostItemService lostItemService) {
        this.lostItemService = lostItemService;
    }

    // role চেক করার জন্য ছোট্ট helper method
    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    @GetMapping
    public String getAllLostItems(Model model, Authentication authentication) {

        boolean admin = isAdmin(authentication);
        List<LostItem> items;

        if (admin) {
            items = lostItemService.getAllLostItems();
        } else {
            items = lostItemService.getLostItemsByUser(authentication.getName());
        }

        model.addAttribute("lostItems", items);
        model.addAttribute("isAdmin", admin);
        return "lost-items";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("lostItem", new LostItem());
        return "lost-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, Authentication authentication) {
        LostItem item = lostItemService.getLostItem(id);

        if (item == null || !item.getCreatedBy().equals(authentication.getName())) {
            return "redirect:/lost";
        }

        model.addAttribute("lostItem", item);
        return "lost-form";
    }

    @PostMapping("/save")
    public String saveLostItem(@ModelAttribute LostItem lostItem, Authentication authentication) {

        if (lostItem.getId() == null) {
            lostItem.setCreatedBy(authentication.getName());
            lostItem.setStatus("pending");
            lostItemService.createLostItem(lostItem);
        } else {
            LostItem existing = lostItemService.getLostItem(lostItem.getId());
            if (existing != null && existing.getCreatedBy().equals(authentication.getName())) {
                lostItemService.updateLostItem(lostItem.getId(), lostItem);
            }
        }

        return "redirect:/lost";
    }

    @GetMapping("/delete/{id}")
    public String deleteLostItem(@PathVariable Long id, Authentication authentication) {
        LostItem item = lostItemService.getLostItem(id);

        if (item != null && item.getCreatedBy().equals(authentication.getName())) {
            lostItemService.deleteLostItem(id);
        }

        return "redirect:/lost";
    }

    // শুধু Admin ব্যবহার করবে - status বদলানোর জন্য
    @PostMapping("/status/{id}")
    public String updateStatus(@PathVariable Long id, @RequestParam String status) {
        lostItemService.updateStatus(id, status);
        return "redirect:/lost";
    }
}