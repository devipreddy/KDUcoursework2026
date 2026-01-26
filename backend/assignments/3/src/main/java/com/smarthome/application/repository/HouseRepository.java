package com.smarthome.application.repository;

import com.smarthome.application.entity.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseRepository extends JpaRepository<House,Long> {
    @Query("SELECT h FROM House h WHERE h.deletedDate IS NULL")
    List<House> findAllActiveHouses();

    @Query("SELECT h FROM House h WHERE h.deletedDate IS NOT NULL")
    List<House> findAllDeletedHouses();

    @Query("SELECT h FROM House h WHERE h.id = :id AND h.deletedDate IS NULL")
    House findActiveHouseById(@Param("id") Long id);
}
