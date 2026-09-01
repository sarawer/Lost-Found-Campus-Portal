package me.sarawer.lost_and_found_campus_portal.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.sarawer.lost_and_found_campus_portal.entity.FoundItem;
import me.sarawer.lost_and_found_campus_portal.service.FoundItemService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        List<FoundItem> items=foundItemService.allFoundItems();

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
    public String saveFoundItem(@Valid @ModelAttribute("foundItem") FoundItem foundItem,
                                BindingResult bindingResult,
                                @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                                Authentication authentication,
                                Model model) throws IOException {
        FoundItem existing = null;
        if (foundItem.getId() != null) {
            existing = foundItemService.getFoundItem(foundItem.getId());
            if (existing == null || !existing.getCreatedBy().equals(authentication.getName())) {
                return "redirect:/found";
            }
            foundItem.setCreatedBy(existing.getCreatedBy());
            foundItem.setStatus(existing.getStatus());
            if (imageFile == null || imageFile.isEmpty()) {
                foundItem.setImageData(existing.getImageData());
                foundItem.setImageContentType(existing.getImageContentType());
            }
        }

        if (imageFile != null && !imageFile.isEmpty()) {
            String contentType = imageFile.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                model.addAttribute("imageUploadError", "Please upload a valid image file.");
                return "found-form";
            }
            foundItem.setImageData(imageFile.getBytes());
            foundItem.setImageContentType(contentType);
        }

        if (bindingResult.hasErrors()) {
            return "found-form";
        }

        if (foundItem.getId() == null) {
            foundItem.setCreatedBy(authentication.getName());
            foundItem.setStatus("pending");
            foundItemService.createFoundItem(foundItem);
        } else {
            foundItemService.updateFoundItem(foundItem.getId(), foundItem);
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