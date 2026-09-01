package me.sarawer.lost_and_found_campus_portal.service;

import me.sarawer.lost_and_found_campus_portal.entity.FoundItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class FoundItemServiceTest {

    @Autowired
    FoundItemService foundItemService;

    Long testId;

    // ---------- createFoundItem() ----------
    @Test
    void createFoundItemTest() {
        FoundItem item = new FoundItem();
        item.setStudentId("2023100000528");
        item.setItemName("Test Watch");
        item.setFoundLocation("Cafeteria");
        item.setStatus("pending");
        item.setCreatedBy("sara2");

        FoundItem saved = foundItemService.createFoundItem(item);
        testId = saved.getId();

        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals("Test Watch", saved.getItemName());
    }

    // ---------- getFoundItem() ----------
    @Test
    void getFoundItemTest() {
        FoundItem item = new FoundItem();
        item.setItemName("Sample Umbrella");

        FoundItem saved = foundItemService.createFoundItem(item);
        testId = saved.getId();

        FoundItem found = foundItemService.getFoundItem(saved.getId());

        Assertions.assertNotNull(found);
        Assertions.assertEquals("Sample Umbrella", found.getItemName());
    }

    @Test
    void getFoundItemNotFoundTest() {
        FoundItem result = foundItemService.getFoundItem(999999L);
        Assertions.assertNull(result);
    }

    // ---------- getAllFoundItems() ----------
    @Test
    void getAllFoundItemsTest() {
        FoundItem item = new FoundItem();
        item.setItemName("Item For List Test");

        FoundItem saved = foundItemService.createFoundItem(item);
        testId = saved.getId();

        List<FoundItem> allItems = foundItemService.getAllFoundItems();

        Assertions.assertNotNull(allItems);
        Assertions.assertTrue(allItems.size() > 0);
    }

    // ---------- updateFoundItem() ----------
    @Test
    void updateFoundItemTest() {
        FoundItem item = new FoundItem();
        item.setItemName("Old Name");
        item.setStudentId("111");

        FoundItem saved = foundItemService.createFoundItem(item);
        testId = saved.getId();

        FoundItem updateData = new FoundItem();
        updateData.setItemName("Updated Name");
        updateData.setStudentId("222");

        FoundItem updated = foundItemService.updateFoundItem(saved.getId(), updateData);

        Assertions.assertEquals("Updated Name", updated.getItemName());
        Assertions.assertEquals("222", updated.getStudentId());
    }

    @Test
    void updateFoundItemNotFoundTest() {
        FoundItem updateData = new FoundItem();
        updateData.setItemName("Doesn't Matter");

        FoundItem result = foundItemService.updateFoundItem(999999L, updateData);

        Assertions.assertNull(result);
    }

    // ---------- deleteFoundItem() ----------
    @Test
    void deleteFoundItemTest() {
        FoundItem item = new FoundItem();
        item.setItemName("To Be Deleted");

        FoundItem saved = foundItemService.createFoundItem(item);

        boolean result = foundItemService.deleteFoundItem(saved.getId());

        Assertions.assertTrue(result);
    }

    @Test
    void deleteFoundItemNotFoundTest() {
        boolean result = foundItemService.deleteFoundItem(999999L);
        Assertions.assertFalse(result);
    }

    // ---------- getFoundItemByUser() ----------
    @Test
    void getFoundItemByUserTest() {
        FoundItem item = new FoundItem();
        item.setItemName("User Owned Item");
        item.setCreatedBy("testUser123");

        FoundItem saved = foundItemService.createFoundItem(item);
        testId = saved.getId();

        List<FoundItem> userItems = foundItemService.getFoundItemByUser("testUser123");

        Assertions.assertNotNull(userItems);
        Assertions.assertTrue(userItems.size() > 0);
        Assertions.assertEquals("testUser123", userItems.get(0).getCreatedBy());
    }

    // ---------- updateStatus() ----------
    @Test
    void updateStatusTest() {
        FoundItem item = new FoundItem();
        item.setItemName("Status Test Item");
        item.setStatus("pending");

        FoundItem saved = foundItemService.createFoundItem(item);
        testId = saved.getId();

        FoundItem updated = foundItemService.updateStatus(saved.getId(), "resolved");

        Assertions.assertEquals("resolved", updated.getStatus());
    }

    @Test
    void updateStatusNotFoundTest() {
        FoundItem result = foundItemService.updateStatus(999999L, "resolved");
        Assertions.assertNull(result);
    }
}