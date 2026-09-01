package me.sarawer.lost_and_found_campus_portal.repository;

import me.sarawer.lost_and_found_campus_portal.entity.FoundItem;
import me.sarawer.lost_and_found_campus_portal.entity.ItemComment;
import me.sarawer.lost_and_found_campus_portal.entity.LostItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemCommentRepository extends JpaRepository<ItemComment, Long> {
    List<ItemComment> findByLostItemOrderByCreatedAtAsc(LostItem lostItem);
    List<ItemComment> findByFoundItemOrderByCreatedAtAsc(FoundItem foundItem);
}
