package me.sarawer.lost_and_found_campus_portal.controller;

import me.sarawer.lost_and_found_campus_portal.entity.FoundItem;
import me.sarawer.lost_and_found_campus_portal.entity.LostItem;
import me.sarawer.lost_and_found_campus_portal.service.FoundItemService;
import me.sarawer.lost_and_found_campus_portal.service.LostItemService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MyReportsController {

    private final LostItemService lostItemService;
    private final FoundItemService foundItemService;

    public MyReportsController(LostItemService lostItemService, FoundItemService foundItemService) {
        this.lostItemService = lostItemService;
        this.foundItemService = foundItemService;
    }

    @GetMapping("/my-reports")
    public String myReports(Model model, Authentication authentication) {
        String username = authentication.getName();

        List<LostItem> myLostItems = lostItemService.getLostItemsByUser(username);
        List<FoundItem> myFoundItems = foundItemService.getFoundItemByUser(username);

        model.addAttribute("myLostItems", myLostItems);
        model.addAttribute("myFoundItems", myFoundItems);
        return "myReport";
    }
}