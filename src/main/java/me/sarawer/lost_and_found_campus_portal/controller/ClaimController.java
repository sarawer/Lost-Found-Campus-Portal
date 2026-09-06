package me.sarawer.lost_and_found_campus_portal.controller;

import lombok.RequiredArgsConstructor;
import me.sarawer.lost_and_found_campus_portal.service.FoundItemService;
import me.sarawer.lost_and_found_campus_portal.service.LostItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/claims")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class ClaimController {

    private final LostItemService lostItemService;
    private final FoundItemService foundItemService;


    @GetMapping
    public String showAdminDashboard(Model model) {
        model.addAttribute("pendingLostItems", lostItemService.getPendingLostItems());
        model.addAttribute("pendingFoundItems", foundItemService.getPendingFoundItems());
        return "claims";
    }

    @PostMapping("/lost/{id}/approve")
    public String approveLost(@PathVariable Long id) {
        lostItemService.updateStatus(id, "approved");
        return "redirect:/claims";
    }

    @PostMapping("/lost/{id}/delete")
    public String deleteLost(@PathVariable Long id) {
        lostItemService.deleteLostItem(id);
        return "redirect:/claims";
    }

    @PostMapping("/found/{id}/approve")
    public String approveFound(@PathVariable Long id) {
        foundItemService.updateStatus(id, "approved");
        return "redirect:/claims";
    }

    @PostMapping("/found/{id}/delete")
    public String deleteFound(@PathVariable Long id) {
        foundItemService.deleteFoundItem(id);
        return "redirect:/claims";
    }
}
