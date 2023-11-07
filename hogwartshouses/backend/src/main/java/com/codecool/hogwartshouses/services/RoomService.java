package com.codecool.hogwartshouses.services;

import com.codecool.hogwartshouses.constants.Constants;
import com.codecool.hogwartshouses.data.Room;
import com.codecool.hogwartshouses.data.Student;
import com.codecool.hogwartshouses.exceptions.roomExceptions.*;
import com.codecool.hogwartshouses.exceptions.studentExceptions.StudentNotFoundException;
import com.codecool.hogwartshouses.repositories.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final StudentService studentService;

    public RoomService(final RoomRepository roomRepository, final StudentService studentService) {
        this.roomRepository = roomRepository;
        this.studentService = studentService;
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room saveOneRoom(final Room room) {
        return roomRepository.save(room);
    }

    public Room getRoomById(final Long roomId) throws RoomNotFoundException {
        return roomRepository.findById(roomId).orElseThrow(RoomNotFoundException::new);
    }

    public Long removeOneRoomById(final Long roomId) throws RoomNotFoundException {
        Room room = getRoomById(roomId);
        List<Student> studentList = room.getStudentList();
        for(Student student : studentList){
            student.setRoom(null);
        }
        roomRepository.deleteById(roomId);
        return roomId;//return the roomId which was deleted
    }

    public Room updateRoomById(final Long roomId, final Room inputRoom) throws RoomNotFoundException {
        Room room = getRoomById(roomId);
        room.setCappacity(inputRoom.getCappacity());
        return roomRepository.save(room);
    }

    public Room addStudentToRoomById(final Long roomId, final Long studentId) throws RoomNotFoundException, UserAleadyInRoomException, RoomIsFullException, StudentNotFoundException, RoomOccupancyFailureException {
        Student student = studentService.getStudentById(studentId);
        Room room = getRoomById(roomId);
        if(room.getStudentList().contains(student)){
            throw new UserAleadyInRoomException();
        } else if(room.getCappacity() <= room.getStudentList().size()) {
            throw new RoomIsFullException();
        } else {
            Room oldRoom = student.getRoom();
            if(oldRoom!=null){
                if(oldRoom.getOccupancy()<=0){
                    throw new RoomOccupancyFailureException();
                }
                oldRoom.setOccupancy(oldRoom.getOccupancy()-1);
                //User is auto removed after assigning to new Room
            }
            room.getStudentList().add(student);
            room.setOccupancy(room.getOccupancy()+1);
            student.setRoom(room);
            student.setRoomIdentification(room.getId());
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
