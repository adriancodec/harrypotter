package com.codecool.hogwartshouses.controllers;

import com.codecool.hogwartshouses.data.Room;
import com.codecool.hogwartshouses.exceptions.roomExceptions.*;
import com.codecool.hogwartshouses.exceptions.studentExceptions.StudentNotFoundException;
import com.codecool.hogwartshouses.services.RoomService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
    Long deleteRoomById(@PathVariable Long roomId) throws RoomNotFoundException {
        return roomService.removeOneRoomById(roomId);
    }

    @PutMapping("{roomId}")
    Room updateRoomById(@PathVariable Long roomId, @RequestBody Room room) throws RoomNotFoundException {
        return roomService.updateRoomById(roomId, room);
    }

    @PutMapping("{roomId}/addStudent")
    Room addStudentToRoom(@PathVariable Long roomId, @RequestParam Long studentId) throws RoomNotFoundException, UserAleadyInRoomException, RoomIsFullException, StudentNotFoundException, RoomOccupancyFailureException {
        return roomService.addStudentToRoomById(roomId, studentId);
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
