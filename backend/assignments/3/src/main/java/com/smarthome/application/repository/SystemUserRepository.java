package com.smarthome.application.repository;

import com.smarthome.application.entity.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SystemUserRepository extends JpaRepository<SystemUser, Long> {
    Optional<SystemUser> findByUsername(String username);

    @Query("SELECT u FROM SystemUser u WHERE u.deletedDate IS NULL")
    List<SystemUser> findAllActiveUsers();

    @Query("SELECT u FROM SystemUser u WHERE u.deletedDate IS NOT NULL")
    List<SystemUser> findAllDeletedUsers();

    @Query("SELECT u FROM SystemUser u WHERE u.id = :id AND u.deletedDate IS NULL")
    SystemUser findActiveUserById(@Param("id") Long id);
}
