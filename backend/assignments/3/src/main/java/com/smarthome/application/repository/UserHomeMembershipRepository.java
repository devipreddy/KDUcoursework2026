package com.smarthome.application.repository;

import com.smarthome.application.entity.House;
import com.smarthome.application.entity.SystemUser;
import com.smarthome.application.entity.UserHomeMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserHomeMembershipRepository extends JpaRepository<UserHomeMembership, Long> {

    List<UserHomeMembership> findByUser(SystemUser user);

    boolean existsByHouseAndUser(House house, SystemUser user);

    // Custom query to include soft-deleted entities if needed
    @Query("SELECT m FROM UserHomeMembership m WHERE m.deletedDate IS NULL")
    List<UserHomeMembership> findAllActiveMemberships();

    @Query("SELECT m FROM UserHomeMembership m WHERE m.deletedDate IS NOT NULL")
    List<UserHomeMembership> findAllDeletedMemberships();

    @Query("SELECT m FROM UserHomeMembership m WHERE m.id = :id AND m.deletedDate IS NULL")
    UserHomeMembership findActiveMembershipById(@Param("id") Long id);
}
