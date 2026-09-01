package me.sarawer.lost_and_found_campus_portal.service;

import me.sarawer.lost_and_found_campus_portal.entity.LostItem;
import me.sarawer.lost_and_found_campus_portal.repository.LostItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LostItemService {
    private LostItemRepository lostItemRepository;

    public LostItemService(LostItemRepository lostItemRepository) {
        this.lostItemRepository = lostItemRepository;
    }

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
        updatedLostItem.setImageData(lostItemReq.getImageData());
        updatedLostItem.setImageContentType(lostItemReq.getImageContentType());

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
