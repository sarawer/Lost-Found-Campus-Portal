package me.sarawer.lost_and_found_campus_portal.service;

import lombok.RequiredArgsConstructor;
import me.sarawer.lost_and_found_campus_portal.entity.Claim;
import me.sarawer.lost_and_found_campus_portal.repository.ClaimRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClaimService {
    private final ClaimRepository claimRepository;

    public Claim createClaim(Claim claimReq) {
        return claimRepository.save(claimReq);
    }

    public Claim getClaim(Long id) {
        return claimRepository.findById(id).orElse(null);
    }

    public List<Claim> getAllClaims() {
        return claimRepository.findAll();
    }

    public Claim updateClaim(Long id, Claim claimReq) {
        Optional<Claim> existingClaim = claimRepository.findById(id);

        if (existingClaim.isEmpty()) return null;

        Claim updatedClaim = existingClaim.get();
        updatedClaim.setItemName(claimReq.getItemName());
        updatedClaim.setClaimerStudentId(claimReq.getClaimerStudentId());
        updatedClaim.setClaimerName(claimReq.getClaimerName());
        updatedClaim.setClaimerContact(claimReq.getClaimerContact());
        updatedClaim.setClaimDate(claimReq.getClaimDate());
        updatedClaim.setStatus(claimReq.getStatus());

        return claimRepository.save(updatedClaim);
    }

    public boolean deleteClaim(Long id) {
        if (!claimRepository.existsById(id)) {
            return false;
        }
        claimRepository.deleteById(id);
        return true;
    }
}
