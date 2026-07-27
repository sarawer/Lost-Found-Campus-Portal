package me.sarawer.lost_and_found_campus_portal.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;
    private String claimerStudentId;
    private String claimerName;
    private String claimerContact;
    private LocalDate claimDate;
    private String status;   // "pending" / "approved" / "rejected"

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getClaimerStudentId() {
        return claimerStudentId;
    }

    public void setClaimerStudentId(String claimerStudentId) {
        this.claimerStudentId = claimerStudentId;
    }

    public String getClaimerName() {
        return claimerName;
    }

    public void setClaimerName(String claimerName) {
        this.claimerName = claimerName;
    }

    public String getClaimerContact() {
        return claimerContact;
    }

    public void setClaimerContact(String claimerContact) {
        this.claimerContact = claimerContact;
    }

    public LocalDate getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(LocalDate claimDate) {
        this.claimDate = claimDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
