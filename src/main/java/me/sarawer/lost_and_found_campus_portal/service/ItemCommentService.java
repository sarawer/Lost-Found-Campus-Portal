package me.sarawer.lost_and_found_campus_portal.service;

import me.sarawer.lost_and_found_campus_portal.entity.AppUser;
import me.sarawer.lost_and_found_campus_portal.entity.FoundItem;
import me.sarawer.lost_and_found_campus_portal.entity.ItemComment;
import me.sarawer.lost_and_found_campus_portal.entity.LostItem;
import me.sarawer.lost_and_found_campus_portal.repository.ItemCommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemCommentService {
    private final ItemCommentRepository itemCommentRepository;

    public ItemCommentService(ItemCommentRepository itemCommentRepository) {
        this.itemCommentRepository = itemCommentRepository;
    }

    public List<ItemComment> getCommentsForLostItem(LostItem lostItem) {
        if (lostItem == null) {
            return List.of();
        }
        return itemCommentRepository.findByLostItemOrderByCreatedAtAsc(lostItem);
    }

    public List<ItemComment> getCommentsForFoundItem(FoundItem foundItem) {
        if (foundItem == null) {
            return List.of();
        }
        return itemCommentRepository.findByFoundItemOrderByCreatedAtAsc(foundItem);
    }

    public ItemComment createCommentForLostItem(LostItem lostItem, AppUser user, String content) {
        String cleaned = content == null ? "" : content.trim();
        if (lostItem == null || user == null || cleaned.isEmpty()) {
            return null;
        }

        ItemComment comment = new ItemComment();
        comment.setLostItem(lostItem);
        comment.setUser(user);
        comment.setContent(cleaned);
        return itemCommentRepository.save(comment);
    }

    public ItemComment createCommentForFoundItem(FoundItem foundItem, AppUser user, String content) {
        String cleaned = content == null ? "" : content.trim();
        if (foundItem == null || user == null || cleaned.isEmpty()) {
            return null;
        }

        ItemComment comment = new ItemComment();
        comment.setFoundItem(foundItem);
        comment.setUser(user);
        comment.setContent(cleaned);
        return itemCommentRepository.save(comment);
    }
}
