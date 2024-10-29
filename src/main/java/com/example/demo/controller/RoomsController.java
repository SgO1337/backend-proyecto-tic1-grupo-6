package com.example.demo.controller;

import com.example.demo.model.Branches;
import com.example.demo.model.Rooms;
import com.example.demo.service.BranchesService;
import com.example.demo.service.RoomsService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController  // Change to RestController for JSON responses
@RequestMapping("/api/rooms")
//@CrossOrigin(origins = "http://localhost:3000")
public class RoomsController {

    private final RoomsService roomsService;
    private final BranchesService branchesService;

    public RoomsController(RoomsService roomsService, BranchesService branchesService) {
        this.roomsService = roomsService;
        this.branchesService = branchesService;
    }

    // List all rooms
    @GetMapping
    public List<Rooms> listRooms() {
        return roomsService.getAllRooms();  // Return a list of rooms in JSON
    }

    // Create a new room
    @PostMapping("/create")
    public ResponseEntity<?> createRoom(@RequestBody Rooms room) {
        if (room.getRoomName() == null || room.getRoomName().isEmpty()) {
            return null;  // Handle incomplete data case (you may want to throw an exception or return a specific error)
        }

        // check if the branch exists
        boolean branchExists = false;
        for (Branches b : branchesService.getAllBranches()) {
            if (b.getIdBranch().equals(room.getBranch().getIdBranch())) {
                branchExists = true;
                break;
            }

            }
        if (!branchExists) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Branch not found");
        }


            for (Rooms r : roomsService.getAllRooms()) {
                if (r.getRoomName().equals(room.getRoomName()) && r.getBranch().equals(room.getBranch())) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("A room with the same name is already created in the same branch");
                    // Handle duplicate room name case (you may want to throw an exception or return a specific error)
                }}

            Rooms savedRoom = roomsService.saveRoom(room);  // Guardar el nuevo room
            return ResponseEntity.status(HttpStatus.CREATED).body(savedRoom);  // Devolver el objeto Rooms como JSON
        }

    // View a specific room by ID
    @GetMapping("/view/{id}")
    public Rooms viewRoom(@PathVariable Long id) {
        Optional<Rooms> optionalRoom = roomsService.getRoomById(id);
        return optionalRoom.orElse(null);  // Return the room or null if not found
    }

    // Update an existing room
    @PutMapping("/update/{id}")
    public Rooms updateRoom(@PathVariable Long id, @RequestBody Rooms updatedRoom) {
        Optional<Rooms> optionalExistingRoom = roomsService.getRoomById(id);
        if (optionalExistingRoom.isPresent()) {
            Rooms existingRoom = optionalExistingRoom.get();
            existingRoom.setRoomName(updatedRoom.getRoomName());
            return roomsService.saveRoom(existingRoom);  // Save and return the updated room
        }
        return null;  // Handle not found case (you may want to throw an exception or return a specific error)
    }

    // Delete a room
    @DeleteMapping("/delete/{id}")
    public boolean deleteRoom(@PathVariable Long id) {
        return roomsService.deleteRoom(id);  // Delete the room by ID and return success status
    }
    }
