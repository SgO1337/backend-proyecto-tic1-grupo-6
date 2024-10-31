package com.example.demo.controller;

import com.example.demo.model.Branches;
import com.example.demo.model.Rooms;
import com.example.demo.service.BranchesService;
import com.example.demo.service.RoomsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * RoomsController is a REST controller that manages room-related operations.
 * It provides endpoints for listing, creating, viewing, updating, and deleting rooms.
 */
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

    /**
     * Lists all rooms.
     *
     * @return a list of rooms in JSON format
     */
    @GetMapping
    public List<Rooms> listRooms() {
        return roomsService.getAllRooms();  // Return a list of rooms in JSON
    }

    /**
     * Creates a new room.
     *
     * @param room the room details to create
     * @return a ResponseEntity containing the created room or an error message
     */
    @PostMapping("/create")
    public ResponseEntity<?> createRoom(@RequestBody Rooms room) {
        if (room.getRoomName() == null || room.getRoomName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Room name is required.");
        }

        // Check if the branch exists
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

        // Check for duplicate room name in the same branch
        for (Rooms r : roomsService.getAllRooms()) {
            if (r.getRoomName().equals(room.getRoomName()) && r.getBranch().equals(room.getBranch())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("A room with the same name is already created in the same branch");
            }
        }

        Rooms savedRoom = roomsService.saveRoom(room);  // Save the new room
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRoom);  // Return the object Rooms as JSON
    }

    /**
     * Views a specific room by its ID.
     *
     * @param id the ID of the room to view
     * @return the room object or null if not found
     */
    @GetMapping("/view/{id}")
    public Rooms viewRoom(@PathVariable Long id) {
        Optional<Rooms> optionalRoom = roomsService.getRoomById(id);
        return optionalRoom.orElse(null);  // Return the room or null if not found
    }

    /**
     * Updates an existing room.
     *
     * @param id the ID of the room to update
     * @param updatedRoom the updated room details
     * @return the updated room or null if not found
     */
    @PutMapping("/update/{id}")
    public Rooms updateRoom(@PathVariable Long id, @RequestBody Rooms updatedRoom) {
        Optional<Rooms> optionalExistingRoom = roomsService.getRoomById(id);
        if (optionalExistingRoom.isPresent()) {
            Rooms existingRoom = optionalExistingRoom.get();
            existingRoom.setRoomName(updatedRoom.getRoomName());
            return roomsService.saveRoom(existingRoom);  // Save and return the updated room
        }
        return null;  // Handle not found case
    }

    /**
     * Deletes a room by its ID.
     *
     * @param id the ID of the room to delete
     * @return a boolean indicating success or failure
     */
    @DeleteMapping("/delete/{id}")
    public boolean deleteRoom(@PathVariable Long id) {
        return roomsService.deleteRoom(id);  // Delete the room by ID and return success status
    }
}