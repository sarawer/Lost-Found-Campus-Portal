package me.sarawer.lost_and_found_campus_portal.controller;

import lombok.RequiredArgsConstructor;
import me.sarawer.lost_and_found_campus_portal.entity.FoundItem;
import me.sarawer.lost_and_found_campus_portal.service.FoundItemService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/found")
public class FoundItemController {

    private final FoundItemService foundItemService;


    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    @GetMapping
    public String getAllFoundItems(Model model, Authentication authentication) {

        boolean admin = isAdmin(authentication);
        List<FoundItem> items;

        if (admin) {
            items =foundItemService.getAllFoundItems();
        } else {
            items = foundItemService.getFoundItemByUser(authentication.getName());
        }

        model.addAttribute("foundItems", items);
        model.addAttribute("isAdmin", admin);
        return "found-items";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("foundItem", new FoundItem());
        return "found-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, Authentication authentication) {
        FoundItem item = foundItemService.getFoundItem(id);

        if (item == null || !item.getCreatedBy().equals(authentication.getName())) {
            return "redirect:/found";
        }

        model.addAttribute("foundItem", item);
        return "found-form";
    }

    @PostMapping("/save")
    public String saveFoundItem(@ModelAttribute FoundItem foundItem, Authentication authentication) {

        if (foundItem.getId() == null) {
            foundItem.setCreatedBy(authentication.getName());
            foundItem.setStatus("pending");
            foundItemService.createFoundItem(foundItem);
        } else {
            FoundItem existing = foundItemService.getFoundItem(foundItem.getId());
            if (existing != null && existing.getCreatedBy().equals(authentication.getName())) {
                foundItemService.updateFoundItem(foundItem.getId(), foundItem);
            }
        }

        return "redirect:/found";
    }

    @GetMapping("/delete/{id}")
    public String deleteFoundItem(@PathVariable Long id, Authentication authentication) {
        FoundItem item = foundItemService.getFoundItem(id);

        if (item != null && item.getCreatedBy().equals(authentication.getName())) {
            foundItemService.deleteFoundItem(id);
        }

        return "redirect:/found";
    }

    @PostMapping("/status/{id}")
    public String updateStatus(@PathVariable Long id, @RequestParam String status) {
        foundItemService.updateStatus(id, status);
        return "redirect:/found";
    }
}