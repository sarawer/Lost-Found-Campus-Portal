package me.sarawer.lost_and_found_campus_portal.controller;



import me.sarawer.lost_and_found_campus_portal.entity.FoundItem;
import me.sarawer.lost_and_found_campus_portal.entity.LostItem;
import me.sarawer.lost_and_found_campus_portal.service.FoundItemService;
import me.sarawer.lost_and_found_campus_portal.service.LostItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;




@RequestMapping("/browseItems")
@Controller
public class AlItemController {
    private final FoundItemService foundItemService;
    private final LostItemService lostItemService;

    public AlItemController(FoundItemService foundItemService, LostItemService lostItemService) {
        this.foundItemService = foundItemService;
        this.lostItemService = lostItemService;
    }

    @GetMapping
    public String browseItems(Model model) {

        List<LostItem> lostItems=lostItemService.getAllLostItems();

        List<FoundItem> foundItems=foundItemService.getAllFoundItems();

        model.addAttribute("lostItems", lostItems);
        model.addAttribute("foundItems", foundItems);

        return "browseItems";
    }
}
