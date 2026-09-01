package me.sarawer.lost_and_found_campus_portal.service;

import me.sarawer.lost_and_found_campus_portal.entity.LostItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class LostItemServiceTest {

    @Autowired
    LostItemService lostItemService;

    Long testId;

    // ---------- createLostItem() ----------
    @Test
    void createLostItemTest() {
        LostItem item = new LostItem();
        item.setStudentId("2023100000528");
        item.setItemName("Test Wallet");
        item.setLostLocation("Library");
        item.setStatus("pending");
        item.setCreatedBy("sara2");

        LostItem saved = lostItemService.createLostItem(item);
        testId = saved.getId();

        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals("Test Wallet", saved.getItemName());
    }

    // ---------- getLostItem() ----------
    @Test
    void getLostItemTest() {
        LostItem item = new LostItem();
        item.setItemName("Sample Bag");

        LostItem saved = lostItemService.createLostItem(item);
        testId = saved.getId();

        LostItem found = lostItemService.getLostItem(saved.getId());

        Assertions.assertNotNull(found);
        Assertions.assertEquals("Sample Bag", found.getItemName());
    }

    @Test
    void getLostItemNotFoundTest() {
        LostItem result = lostItemService.getLostItem(999999L);
        Assertions.assertNull(result);
    }

    // ---------- getAllLostItems() ----------
    @Test
    void getAllLostItemsTest() {
        LostItem item = new LostItem();
        item.setItemName("Item For List Test");

        LostItem saved = lostItemService.createLostItem(item);
        testId = saved.getId();

        List<LostItem> allItems = lostItemService.getAllLostItems();

        Assertions.assertNotNull(allItems);
        Assertions.assertTrue(allItems.size() > 0);
    }

    // ---------- updateLostItem() ----------
    @Test
    void updateLostItemTest() {
        LostItem item = new LostItem();
        item.setItemName("Old Name");
        item.setStudentId("111");

        LostItem saved = lostItemService.createLostItem(item);
        testId = saved.getId();

        LostItem updateData = new LostItem();
        updateData.setItemName("Updated Name");
        updateData.setStudentId("222");

        LostItem updated = lostItemService.updateLostItem(saved.getId(), updateData);

        Assertions.assertEquals("Updated Name", updated.getItemName());
        Assertions.assertEquals("222", updated.getStudentId());
    }

    @Test
    void updateLostItemNotFoundTest() {
        LostItem updateData = new LostItem();
        updateData.setItemName("Doesn't Matter");

        LostItem result = lostItemService.updateLostItem(999999L, updateData);

        Assertions.assertNull(result);
    }

    // ---------- deleteLostItem() ----------
    @Test
    void deleteLostItemTest() {
        LostItem item = new LostItem();
        item.setItemName("To Be Deleted");

        LostItem saved = lostItemService.createLostItem(item);

        boolean result = lostItemService.deleteLostItem(saved.getId());

        Assertions.assertTrue(result);
    }

    @Test
    void deleteLostItemNotFoundTest() {
        boolean result = lostItemService.deleteLostItem(999999L);
        Assertions.assertFalse(result);
    }

    // ---------- getLostItemsByUser() ----------
    @Test
    void getLostItemsByUserTest() {
        LostItem item = new LostItem();
        item.setItemName("User Owned Item");
        item.setCreatedBy("testUser123");

        LostItem saved = lostItemService.createLostItem(item);
        testId = saved.getId();

        List<LostItem> userItems = lostItemService.getLostItemsByUser("testUser123");

        Assertions.assertNotNull(userItems);
        Assertions.assertTrue(userItems.size() > 0);
        Assertions.assertEquals("testUser123", userItems.get(0).getCreatedBy());
    }

    // ---------- updateStatus() ----------
    @Test
    void updateStatusTest() {
        LostItem item = new LostItem();
        item.setItemName("Status Test Item");
        item.setStatus("pending");

        LostItem saved = lostItemService.createLostItem(item);
        testId = saved.getId();

        LostItem updated = lostItemService.updateStatus(saved.getId(), "resolved");

        Assertions.assertEquals("resolved", updated.getStatus());
    }

    @Test
    void updateStatusNotFoundTest() {
        LostItem result = lostItemService.updateStatus(999999L, "resolved");
        Assertions.assertNull(result);
    }
}