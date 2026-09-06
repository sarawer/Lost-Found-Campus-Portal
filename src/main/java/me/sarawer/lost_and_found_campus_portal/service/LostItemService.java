package me.sarawer.lost_and_found_campus_portal.service;

import lombok.RequiredArgsConstructor;
import me.sarawer.lost_and_found_campus_portal.entity.LostItem;
import me.sarawer.lost_and_found_campus_portal.repository.LostItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LostItemService {
    private final LostItemRepository lostItemRepository;

    public LostItem createLostItem(LostItem lostItemReq) {
        LostItem lostItemResp = lostItemRepository.save(lostItemReq);
        return lostItemResp;
    }

    public LostItem getLostItem(Long id) {
        return lostItemRepository.findById(id).orElse(null);
    }

    public List<LostItem> getAllLostItems() {
        return lostItemRepository.findAll();
    }

    public List<LostItem> getApprovedLostItems() {
        return lostItemRepository.findByStatus("approved");
    }

    public List<LostItem> searchApprovedLostItems(String query) {
        List<LostItem> items = getApprovedLostItems();
        if (query == null || query.trim().isEmpty()) {
            return items;
        }

        String searchText = query.trim().toLowerCase();
        return items.stream()
                .filter(item -> itemMatches(item, searchText))
                .toList();
    }

    public List<LostItem> searchAllLostItems(String query) {
        List<LostItem> items = allLostItems();
        if (query == null || query.trim().isEmpty()) {
            return items;
        }

        String searchText = query.trim().toLowerCase();
        return items.stream()
                .filter(item -> itemMatches(item, searchText))
                .toList();
    }

    private boolean itemMatches(LostItem item, String searchText) {
        if (item == null) {
            return false;
        }

        return (item.getItemName() != null && item.getItemName().toLowerCase().contains(searchText))
                || (item.getDescription() != null && item.getDescription().toLowerCase().contains(searchText))
                || (item.getLostLocation() != null && item.getLostLocation().toLowerCase().contains(searchText))
                || (item.getStudentId() != null && item.getStudentId().toLowerCase().contains(searchText))
                || (item.getCreatedBy() != null && item.getCreatedBy().toLowerCase().contains(searchText));
    }

    public List<LostItem> getPendingLostItems() {
        return lostItemRepository.findByStatus("pending");
    }

    public LostItem updateLostItem(Long id, LostItem lostItemReq) {
        Optional<LostItem> existingLostItem = lostItemRepository.findById(id);

        if (existingLostItem.isEmpty()) return null;

        LostItem updatedLostItem = existingLostItem.get();

        updatedLostItem.setStudentId(lostItemReq.getStudentId());
        updatedLostItem.setItemName(lostItemReq.getItemName());
        updatedLostItem.setDescription(lostItemReq.getDescription());
        updatedLostItem.setLostLocation(lostItemReq.getLostLocation());
        updatedLostItem.setLostDate(lostItemReq.getLostDate());
        updatedLostItem.setContactInfo(lostItemReq.getContactInfo());
        updatedLostItem.setImageUrl(lostItemReq.getImageUrl());

        return lostItemRepository.save(updatedLostItem);
    }

    public boolean deleteLostItem(Long id) {
        if (!lostItemRepository.existsById(id)) {
            return false;
        }
        lostItemRepository.deleteById(id);
        return true;
    }

    public List<LostItem>allLostItems(){
        return lostItemRepository.findAll();
    }

    public List<LostItem> getLostItemsByUser(String username) {
        return lostItemRepository.findByCreatedBy(username);
    }

    public LostItem updateStatus(Long id, String status) {
        LostItem item = lostItemRepository.findById(id).orElse(null);
        if (item == null) return null;

        item.setStatus(status);
        return lostItemRepository.save(item);
    }
}
