package me.sarawer.lost_and_found_campus_portal.service;

import me.sarawer.lost_and_found_campus_portal.entity.FoundItem;
import me.sarawer.lost_and_found_campus_portal.entity.LostItem;
import me.sarawer.lost_and_found_campus_portal.repository.FoundItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoundItemService {
    private final FoundItemRepository foundItemRepository;

    public FoundItemService(FoundItemRepository foundItemRepository) {
        this.foundItemRepository = foundItemRepository;
    }

    public FoundItem createFoundItem(FoundItem foundItemReq) {
        return foundItemRepository.save(foundItemReq);
    }

    public FoundItem getFoundItem(Long id) {
        return foundItemRepository.findById(id).orElse(null);
    }

    public List<FoundItem> getAllFoundItems() {
        return foundItemRepository.findAll();

    }

    public List<FoundItem> getApprovedFoundItems() {
        return foundItemRepository.findByStatus("approved");
    }

    public List<FoundItem> searchApprovedFoundItems(String query) {
        List<FoundItem> items = getApprovedFoundItems();
        if (query == null || query.trim().isEmpty()) {
            return items;
        }

        String searchText = query.trim().toLowerCase();
        return items.stream()
                .filter(item -> itemMatches(item, searchText))
                .toList();
    }

    public List<FoundItem> searchAllFoundItems(String query) {
        List<FoundItem> items = allFoundItems();
        if (query == null || query.trim().isEmpty()) {
            return items;
        }

        String searchText = query.trim().toLowerCase();
        return items.stream()
                .filter(item -> itemMatches(item, searchText))
                .toList();
    }

    private boolean itemMatches(FoundItem item, String searchText) {
        if (item == null) {
            return false;
        }

        return (item.getItemName() != null && item.getItemName().toLowerCase().contains(searchText))
                || (item.getDescription() != null && item.getDescription().toLowerCase().contains(searchText))
                || (item.getFoundLocation() != null && item.getFoundLocation().toLowerCase().contains(searchText))
                || (item.getStudentId() != null && item.getStudentId().toLowerCase().contains(searchText))
                || (item.getCreatedBy() != null && item.getCreatedBy().toLowerCase().contains(searchText));
    }

    public List<FoundItem> getPendingFoundItems() {
        return foundItemRepository.findByStatus("pending");
    }

    public FoundItem updateFoundItem(Long id, FoundItem foundItemReq) {
        Optional<FoundItem> existingFoundItem = foundItemRepository.findById(id);

        if (existingFoundItem.isEmpty()) return null;

        FoundItem updatedFoundItem = existingFoundItem.get();
        updatedFoundItem.setStudentId(foundItemReq.getStudentId());
        updatedFoundItem.setItemName(foundItemReq.getItemName());
        updatedFoundItem.setDescription(foundItemReq.getDescription());
        updatedFoundItem.setFoundLocation(foundItemReq.getFoundLocation());
        updatedFoundItem.setFoundDate(foundItemReq.getFoundDate());
        updatedFoundItem.setContactInfo(foundItemReq.getContactInfo());
        updatedFoundItem.setImageData(foundItemReq.getImageData());
        updatedFoundItem.setImageContentType(foundItemReq.getImageContentType());

        return foundItemRepository.save(updatedFoundItem);
    }

    public boolean deleteFoundItem(Long id) {
        if (!foundItemRepository.existsById(id)) {
            return false;
        }
        foundItemRepository.deleteById(id);
        return true;
    }

    public List<FoundItem> allFoundItems() {
        return foundItemRepository.findAll();
    }
    public List<FoundItem> getFoundItemByUser(String username) {
        return foundItemRepository.findByCreatedBy(username);
    }

    public FoundItem updateStatus(Long id, String status) {
        FoundItem item = foundItemRepository.findById(id).orElse(null);
        if (item == null) return null;

        item.setStatus(status);
        return foundItemRepository.save(item);
    }
}
