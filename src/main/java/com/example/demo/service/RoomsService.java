package com.example.demo.service;

import com.example.demo.model.Branches;
import com.example.demo.model.Rooms;
import com.example.demo.repository.BranchesRepository;
import com.example.demo.repository.RoomsRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomsService {

    private final RoomsRepository roomsRepository;
    private final BranchesRepository branchesRepository;  // Inject this repository if needed



    public RoomsService(RoomsRepository roomsRepository, BranchesRepository branchesRepository) {
        this.roomsRepository = roomsRepository;
        this.branchesRepository = branchesRepository;
    }
    // Get all rooms
    public List<Rooms> getAllRooms() {
        return roomsRepository.findAll();
    }

    // Get room by ID
    public Optional<Rooms> getRoomById(Long id) {
        return roomsRepository.findById(id);  // Return Optional to handle room not found
    }

    @Transactional
    public Rooms saveRoom(Rooms room) {
        if (room.getBranch() != null) {
            Branches branch = branchesRepository.findById(room.getBranch().getIdBranch()).orElseThrow(() -> new RuntimeException("Branch not found"));
            room.setBranch(branch);  // Ensure the correct branch is set
        }
        return roomsRepository.save(room);
    }

    public Rooms createRoom(String roomName, String description, Branches branch) {
        Rooms room = new Rooms();
        room.setRoomName(roomName);
        room.setDescription(description);
        room.setBranch(branch);
        return roomsRepository.save(room);
    }
    // Delete a room by ID
    @Transactional
    public boolean deleteRoom(Long id) {
        if (roomsRepository.existsById(id)) {  // Check if room exists before deletion
            roomsRepository.deleteById(id);
            return true;  // Return true if deletion was successful
        }
        return false;  // Return false if room was not found
    }

    // Check if room exists by ID
    public  boolean existsById(Long id) {
        return roomsRepository.existsById(id);  // Check for existence
    }


}
