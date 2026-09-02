package me.sarawer.lost_and_found_campus_portal.controller;

import jakarta.validation.Valid;
import me.sarawer.lost_and_found_campus_portal.entity.AppUser;
import me.sarawer.lost_and_found_campus_portal.entity.ItemComment;
import me.sarawer.lost_and_found_campus_portal.entity.LostItem;
import me.sarawer.lost_and_found_campus_portal.repository.AppUserRepository;
import me.sarawer.lost_and_found_campus_portal.service.ItemCommentService;
import me.sarawer.lost_and_found_campus_portal.service.LostItemService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/lost")
public class LostItemController {

    private final LostItemService lostItemService;
    private final ItemCommentService itemCommentService;
    private final AppUserRepository appUserRepository;

    public LostItemController(LostItemService lostItemService, ItemCommentService itemCommentService, AppUserRepository appUserRepository) {
        this.lostItemService = lostItemService;
        this.itemCommentService = itemCommentService;
        this.appUserRepository = appUserRepository;
    }

    private boolean isAdmin(Authentication authentication) {
        return authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    @GetMapping
    public String getAllLostItems(Model model, Authentication authentication, @RequestParam(value = "query", required = false) String query) {
        boolean admin = isAdmin(authentication);
        List<LostItem> items = admin
                ? lostItemService.searchAllLostItems(query)
                : lostItemService.searchApprovedLostItems(query);
        model.addAttribute("lostItems", items);
        model.addAttribute("isAdmin", admin);
        model.addAttribute("query", query);
        return "lost-items";
    }

    @GetMapping("/{id}")
    public String showLostItemDetail(@PathVariable Long id, Model model) {
        LostItem item = lostItemService.getLostItem(id);
        if (item == null || !"approved".equalsIgnoreCase(item.getStatus())) {
            return "redirect:/lost";
        }

        AppUser reporter = appUserRepository.findAppUsersByUsername(item.getCreatedBy());
        model.addAttribute("item", item);
        model.addAttribute("reporter", reporter);
        model.addAttribute("comments", itemCommentService.getCommentsForLostItem(item));
        model.addAttribute("newComment", new ItemComment());
        return "lost-detail";
    }

    @PostMapping("/{id}/comments")
    public String addLostComment(@PathVariable Long id, @RequestParam("content") String content, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        LostItem item = lostItemService.getLostItem(id);
        if (item == null || !"approved".equalsIgnoreCase(item.getStatus())) {
            return "redirect:/lost";
        }

        AppUser currentUser = appUserRepository.findAppUsersByUsername(authentication.getName());
        if (currentUser == null) {
            return "redirect:/lost/" + id;
        }

        itemCommentService.createCommentForLostItem(item, currentUser, content);
        return "redirect:/lost/" + id;
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
    public String saveLostItem(@Valid @ModelAttribute("lostItem") LostItem lostItem,
                               BindingResult bindingResult,
                               @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                               Authentication authentication,
                               Model model) throws IOException {
        LostItem existing = null;
        if (lostItem.getId() != null) {
            existing = lostItemService.getLostItem(lostItem.getId());
            if (existing == null || !existing.getCreatedBy().equals(authentication.getName())) {
                return "redirect:/lost";
            }
            lostItem.setCreatedBy(existing.getCreatedBy());
            lostItem.setStatus(existing.getStatus());
            lostItem.setCreatedAt(existing.getCreatedAt());
            if (imageFile == null || imageFile.isEmpty()) {
                lostItem.setImageData(existing.getImageData());
                lostItem.setImageContentType(existing.getImageContentType());
            }
        }

        if (imageFile != null && !imageFile.isEmpty()) {
            String contentType = imageFile.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                model.addAttribute("imageUploadError", "Please upload a valid image file.");
                return "lost-form";
            }
            lostItem.setImageData(imageFile.getBytes());
            lostItem.setImageContentType(contentType);
        }

        if (bindingResult.hasErrors()) {
            return "lost-form";
        }

        if (lostItem.getId() == null) {
            lostItem.setCreatedBy(authentication.getName());
            lostItem.setStatus("pending");
            lostItemService.createLostItem(lostItem);
        } else {
            lostItemService.updateLostItem(lostItem.getId(), lostItem);
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

    @PostMapping("/status/{id}")
    public String updateStatus(@PathVariable Long id, @RequestParam String status) {
        lostItemService.updateStatus(id, status);
        return "redirect:/lost";
    }
}
