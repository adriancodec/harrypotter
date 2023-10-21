package com.codecool.hogwartshouses.services;

import com.codecool.hogwartshouses.constants.Constants;
import com.codecool.hogwartshouses.data.Room;
import com.codecool.hogwartshouses.data.Student;
import com.codecool.hogwartshouses.exceptions.roomExceptions.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository, StudentRepository studentRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room saveOneRoom(Room room) {
        return roomRepository.save(room);
    }

    public Room getRoomById(Long roomId) throws RoomNotFoundException {
        return roomRepository.findById(roomId).orElseThrow(RoomNotFoundException::new);
    }

    public Long removeOneRoom(Long roomId) {
        roomRepository.deleteRoomById(roomId);
        return roomId;//return the roomId which was deleted
    }

    public Room updateRoomById(Long roomId, Room inputRoom) throws RoomNotFoundException {
        Room room = roomRepository.findById(roomId).orElseThrow(RoomNotFoundException::new);
        room.setCappacity(inputRoom.getCappacity());
        return roomRepository.save(room);
    }

    public Room addStudentToRoomById(Long roomId, Student student) throws RoomNotFoundException, UserAleadyInRoomException, RoomIsFullException {
        Room room = roomRepository.findById(roomId).orElseThrow(RoomNotFoundException::new);
        if(room.getStudentList().contains(student)){
            throw new UserAleadyInRoomException();
        } else if(room.getCappacity() <= room.getStudentList().size()) {
            throw new RoomIsFullException();
        } else {
            room.getStudentList().add(student);
        }
        return roomRepository.save(room);
    }

    public List<Room> getAvailableRooms() throws NoEmptyRoomAvailable {
        List<Room> listOfAvailableRooms = getAllRooms().stream()
                .filter((room) -> room.getStudentList().size() < room.getCappacity())
                .toList();
        if(listOfAvailableRooms.isEmpty()){
            throw new NoEmptyRoomAvailable();
        }
        return listOfAvailableRooms;
    }

    public List<Room> getAvailableRoomsForRatOwners() throws NoRatOwnerRoomAvailable {
        List<Room> listOfAvailableRooms = getAllRooms().stream()
                .filter((room) -> (room.getStudentList().size() < room.getCappacity()))
                .filter((room) -> (room.getStudentList().stream().noneMatch((student) -> student.getPet().equals(Constants.ANIMAL_CAT) || student.getPet().equals(Constants.ANIMAL_OWL))))
                .toList();
        if(listOfAvailableRooms.isEmpty()){
            throw new NoRatOwnerRoomAvailable();
        }
        return listOfAvailableRooms;
    }
}
