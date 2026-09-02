package me.sarawer.lost_and_found_campus_portal.controller;

import me.sarawer.lost_and_found_campus_portal.entity.FoundItem;
import me.sarawer.lost_and_found_campus_portal.entity.LostItem;
import me.sarawer.lost_and_found_campus_portal.service.FoundItemService;
import me.sarawer.lost_and_found_campus_portal.service.LostItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    private final LostItemService lostItemService;
    private final FoundItemService foundItemService;

    public HomeController(
            LostItemService lostItemService,
            FoundItemService foundItemService
    ) {
        this.lostItemService = lostItemService;
        this.foundItemService = foundItemService;
    }

    @GetMapping("/")
    public String home(Model model) {

        List<Map<String, Object>> recentReports = new ArrayList<>();

        // Get latest 3 approved lost items
        List<LostItem> recentLostItems = lostItemService.getApprovedLostItems()
                .stream()
                .sorted(
                        Comparator.comparing(
                                LostItem::getId,
                                Comparator.nullsLast(Comparator.reverseOrder())
                        )
                )
                .limit(3)
                .toList();

        // Get latest 3 approved found items
        List<FoundItem> recentFoundItems = foundItemService.getApprovedFoundItems()
                .stream()
                .sorted(
                        Comparator.comparing(
                                FoundItem::getId,
                                Comparator.nullsLast(Comparator.reverseOrder())
                        )
                )
                .limit(3)
                .toList();

        // Add lost items to recent reports
        for (LostItem item : recentLostItems) {

            recentReports.add(Map.of(
                    "type", "LOST",

                    "title", item.getItemName() != null
                            ? item.getItemName()
                            : "Unknown item",

                    "location", item.getLostLocation() != null
                            ? item.getLostLocation()
                            : "Location unknown",

                    "reportedAt", item.getLostDate() != null
                            ? item.getLostDate().toString()
                            : "Recently reported",

                    "detailUrl", "/lost/" + item.getId(),

                    "imageDataUri", item.getImageDataUri() != null
                            ? item.getImageDataUri()
                            : "",

                    "id", item.getId()
            ));
        }

        // Add found items to recent reports
        for (FoundItem item : recentFoundItems) {

            recentReports.add(Map.of(
                    "type", "FOUND",

                    "title", item.getItemName() != null
                            ? item.getItemName()
                            : "Unknown item",

                    "location", item.getFoundLocation() != null
                            ? item.getFoundLocation()
                            : "Location unknown",

                    "reportedAt", item.getFoundDate() != null
                            ? item.getFoundDate().toString()
                            : "Recently reported",

                    "detailUrl", "/found/" + item.getId(),

                    "imageDataUri", item.getImageDataUri() != null
                            ? item.getImageDataUri()
                            : "",

                    "id", item.getId()
            ));
        }

        // Sort all reports by ID (latest first)
        recentReports.sort((left, right) ->
                Long.compare(
                        (Long) right.get("id"),
                        (Long) left.get("id")
                )
        );

        // Show maximum 6 recent reports
        model.addAttribute(
                "recentReports",
                recentReports.stream()
                        .limit(6)
                        .toList()
        );

        return "home";
    }
}