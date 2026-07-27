package me.sarawer.lost_and_found_campus_portal.controller;

import me.sarawer.lost_and_found_campus_portal.entity.Claim;
import me.sarawer.lost_and_found_campus_portal.service.ClaimService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("claims")
public class ClaimController {
    private ClaimService claimService;

    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    @GetMapping
    public String getAllClaims(Model model) {
        model.addAttribute("claims", claimService.getAllClaims());
        return "claims";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        Claim claim = new Claim();
        claim.setStatus("pending");   // নতুন claim এ default status
        model.addAttribute("claim", claim);
        return "claim-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Claim claim = claimService.getClaim(id);

        if (claim == null) {
            return "redirect:/claims";
        }

        model.addAttribute("claim", claim);
        return "claim-form";
    }

    @PostMapping("/save")
    public String saveClaim(@ModelAttribute Claim claim) {
        if (claim.getId() == null) {
            claimService.createClaim(claim);
        } else {
            claimService.updateClaim(claim.getId(), claim);
        }
        return "redirect:/claims";
    }

    @GetMapping("/delete/{id}")
    public String deleteClaim(@PathVariable Long id) {
        claimService.deleteClaim(id);
        return "redirect:/claims";
    }
}
