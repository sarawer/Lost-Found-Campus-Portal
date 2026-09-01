package me.sarawer.lost_and_found_campus_portal.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.sarawer.lost_and_found_campus_portal.entity.Claim;
import me.sarawer.lost_and_found_campus_portal.service.ClaimService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("claims")
public class ClaimController {
    private final ClaimService claimService;


    @GetMapping
    public String getAllClaims(Model model) {
        model.addAttribute("claims", claimService.getAllClaims());
        return "claims";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        Claim claim = new Claim();
        claim.setStatus("pending");
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
    public String saveClaim(@Valid @ModelAttribute("claim") Claim claim, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "claim-form";
        }
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
