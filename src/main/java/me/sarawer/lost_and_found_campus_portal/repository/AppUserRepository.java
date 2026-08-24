package me.sarawer.lost_and_found_campus_portal.repository;

import me.sarawer.lost_and_found_campus_portal.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser,Long> {

    AppUser findAppUsersByUsername(String username);
}
