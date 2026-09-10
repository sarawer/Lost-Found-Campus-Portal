package me.sarawer.lost_and_found_campus_portal.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.sarawer.lost_and_found_campus_portal.entity.AppUser;
import me.sarawer.lost_and_found_campus_portal.entity.FoundItem;
import me.sarawer.lost_and_found_campus_portal.entity.ItemComment;
import me.sarawer.lost_and_found_campus_portal.repository.AppUserRepository;
import me.sarawer.lost_and_found_campus_portal.service.CloudinaryService;
import me.sarawer.lost_and_found_campus_portal.service.FoundItemService;
import me.sarawer.lost_and_found_campus_portal.service.ItemCommentService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/found")
@RequiredArgsConstructor
public class FoundItemController {

    private final FoundItemService foundItemService;
    private final ItemCommentService itemCommentService;
    private final AppUserRepository appUserRepository;
    private final CloudinaryService cloudinaryService;


    private boolean isAdmin(Authentication authentication) {
        return authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    @GetMapping
    public String getAllFoundItems(Model model, Authentication authentication, @RequestParam(value = "query", required = false) String query) {
        boolean admin = isAdmin(authentication);
        List<FoundItem> items = admin
                ? foundItemService.searchAllFoundItems(query)
                : foundItemService.searchApprovedFoundItems(query);
        model.addAttribute("foundItems", items);
        model.addAttribute("isAdmin", admin);
        model.addAttribute("query", query);
        return "found-items";
    }

    @GetMapping("/{id}")
    public String showFoundItemDetail(@PathVariable Long id, Model model) {
        FoundItem item = foundItemService.getFoundItem(id);
        if (item == null || !"approved".equalsIgnoreCase(item.getStatus())) {
            return "redirect:/found";
        }

        AppUser reporter = appUserRepository.findAppUsersByUsername(item.getCreatedBy());
        model.addAttribute("item", item);
        model.addAttribute("reporter", reporter);
        model.addAttribute("comments", itemCommentService.getCommentsForFoundItem(item));
        model.addAttribute("newComment", new ItemComment());
        return "found-detail";
    }

    @PostMapping("/{id}/comments")
    public String addFoundComment(@PathVariable Long id, @RequestParam("content") String content, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        FoundItem item = foundItemService.getFoundItem(id);
        if (item == null || !"approved".equalsIgnoreCase(item.getStatus())) {
            return "redirect:/found";
        }

        AppUser currentUser = appUserRepository.findAppUsersByUsername(authentication.getName());
        if (currentUser == null) {
            return "redirect:/found/" + id;
        }

        itemCommentService.createCommentForFoundItem(item, currentUser, content);
        return "redirect:/found/" + id;
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
    public String saveFoundItem(
            @Valid @ModelAttribute("foundItem") FoundItem foundItem,
            BindingResult bindingResult,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            Authentication authentication,
            Model model
    ) throws IOException {

        FoundItem existing = null;

        // Editing existing item
        if (foundItem.getId() != null) {

            existing = foundItemService.getFoundItem(foundItem.getId());

            if (existing == null ||
                    !existing.getCreatedBy().equals(authentication.getName())) {
                return "redirect:/found";
            }

            foundItem.setCreatedBy(existing.getCreatedBy());
            foundItem.setStatus(existing.getStatus());
            foundItem.setCreatedAt(existing.getCreatedAt());

            // No new image → keep old Cloudinary URL
            if (imageFile == null || imageFile.isEmpty()) {
                foundItem.setImageUrl(existing.getImageUrl());
            }
        }

        // New image uploaded
        if (imageFile != null && !imageFile.isEmpty()) {

            String contentType = imageFile.getContentType();

            if (contentType == null || !contentType.startsWith("image/")) {
                model.addAttribute(
                        "imageUploadError",
                        "Please upload a valid image file."
                );

                return "found-form";
            }

            // Upload to Cloudinary
            String imageUrl = cloudinaryService.uploadImage(imageFile);

            // Save only URL in MySQL
            foundItem.setImageUrl(imageUrl);
        }

        if (bindingResult.hasErrors()) {
            return "found-form";
        }

        // New item
        if (foundItem.getId() == null) {

            foundItem.setCreatedBy(authentication.getName());
            foundItem.setStatus("pending");

            foundItemService.createFoundItem(foundItem);

        } else {

            // Existing item
            foundItemService.updateFoundItem(
                    foundItem.getId(),
                    foundItem
            );
        }

        return "redirect:/found";
    }

    @GetMapping("/delete/{id}")
    public String deleteFoundItem(@PathVariable Long id, Authentication authentication) {
        FoundItem item = foundItemService.getFoundItem(id);

        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (item != null && (item.getCreatedBy().equals(authentication.getName()) || isAdmin)) {
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
