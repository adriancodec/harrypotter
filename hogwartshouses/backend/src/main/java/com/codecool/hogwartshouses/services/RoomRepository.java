package com.codecool.hogwartshouses.services;

import com.codecool.hogwartshouses.data.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {

    void deleteRoomById(Long roomId);

}
