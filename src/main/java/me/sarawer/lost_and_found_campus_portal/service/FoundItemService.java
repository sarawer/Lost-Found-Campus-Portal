package me.sarawer.lost_and_found_campus_portal.service;

import lombok.RequiredArgsConstructor;
import me.sarawer.lost_and_found_campus_portal.entity.FoundItem;
import me.sarawer.lost_and_found_campus_portal.entity.LostItem;
import me.sarawer.lost_and_found_campus_portal.repository.FoundItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FoundItemService {
    private final FoundItemRepository foundItemRepository;

    public FoundItem createFoundItem(FoundItem foundItemReq) {
        return foundItemRepository.save(foundItemReq);
    }

    public FoundItem getFoundItem(Long id) {
        return foundItemRepository.findById(id).orElse(null);
    }

    public List<FoundItem> getAllFoundItems() {
        return foundItemRepository.findAll();

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
