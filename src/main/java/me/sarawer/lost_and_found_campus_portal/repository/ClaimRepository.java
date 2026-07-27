package me.sarawer.lost_and_found_campus_portal.repository;

import me.sarawer.lost_and_found_campus_portal.entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaimRepository extends JpaRepository<Claim,Long> {

}
