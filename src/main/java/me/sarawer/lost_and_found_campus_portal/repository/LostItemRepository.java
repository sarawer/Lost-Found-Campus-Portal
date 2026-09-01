package me.sarawer.lost_and_found_campus_portal.repository;

import me.sarawer.lost_and_found_campus_portal.entity.LostItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LostItemRepository extends JpaRepository<LostItem, Long> {
    List<LostItem> findByCreatedBy(String createdBy);
    List<LostItem> findByStatus(String status);
}
