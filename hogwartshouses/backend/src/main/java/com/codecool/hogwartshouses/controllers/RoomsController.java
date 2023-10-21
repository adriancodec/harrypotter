package com.codecool.hogwartshouses.controllers;

import com.codecool.hogwartshouses.data.Room;
import com.codecool.hogwartshouses.data.Student;
import com.codecool.hogwartshouses.exceptions.roomExceptions.*;
import com.codecool.hogwartshouses.services.RoomService;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Transactional
@RequestMapping("rooms")
public class RoomsController {

    private final RoomService roomService;

    public RoomsController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping()
    List<Room> getAllRooms(){
        return roomService.getAllRooms();
    }

    @PostMapping()
    Room addRoom(@RequestBody Room room){
        return roomService.saveOneRoom(room);
    }

    @GetMapping("{roomId}")
    Room getRoomById(@PathVariable Long roomId) throws RoomNotFoundException {
        return roomService.getRoomById(roomId);
    }

    @DeleteMapping("{roomId}")
    Long deleteRoomById(@PathVariable Long roomId) {
        return roomService.removeOneRoom(roomId);
    }

    @PutMapping("{roomId}")
    Room updateRoomById(@PathVariable Long roomId, @RequestBody Room room) throws RoomNotFoundException {
        return roomService.updateRoomById(roomId, room);
    }

    @PutMapping("addStudent/{roomId}")
    Room addStudentToRoom(@PathVariable Long roomId, @RequestBody Student student) throws RoomNotFoundException, UserAleadyInRoomException, RoomIsFullException {
        return roomService.addStudentToRoomById(roomId, student);
    }

    @GetMapping("available")
    List<Room> getAvailableRooms() throws NoEmptyRoomAvailable {
        return roomService.getAvailableRooms();
    }

    @GetMapping("rat-owners")
    List<Room> getAvailableRoomsForRatOwners() throws NoRatOwnerRoomAvailable {
        return roomService.getAvailableRoomsForRatOwners();
    }
}
