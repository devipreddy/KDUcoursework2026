package com.smarthome.application.repository;

import com.smarthome.application.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {
    List<Room> findByHouseHouseId(Long houseId);

    @Query("SELECT r FROM Room r WHERE r.deletedDate IS NULL")
    List<Room> findAllActiveRooms();

    @Query("SELECT r FROM Room r WHERE r.deletedDate IS NOT NULL")
    List<Room> findAllDeletedRooms();

    @Query("SELECT r FROM Room r WHERE r.roomId = :id AND r.deletedDate IS NULL")
    Room findActiveRoomById(@Param("id") Long id);


    @Query("SELECT r FROM Room r WHERE r.house.houseId = :houseId AND r.deletedDate IS NOT NULL")
    List<Room> findAllDeletedRoomsByHouseId(@Param("houseId") Long houseId);

}
