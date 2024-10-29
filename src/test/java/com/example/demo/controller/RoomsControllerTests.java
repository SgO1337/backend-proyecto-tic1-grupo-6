package com.example.demo.controller;

import com.example.demo.model.Branches;
import com.example.demo.model.Rooms;
import com.example.demo.service.BranchesService;
import com.example.demo.service.RoomsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RoomsControllerTests {

    @Mock
    private RoomsService roomsService;

    @Mock
    private BranchesService branchesService;

    @InjectMocks
    private RoomsController roomsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listRooms_ShouldReturnListOfRooms() {
        Rooms room = new Rooms();
        when(roomsService.getAllRooms()).thenReturn(Arrays.asList(room));

        List<Rooms> response = roomsController.listRooms();

        assertEquals(1, response.size());
        verify(roomsService, times(1)).getAllRooms();
    }

    @Test
    void createRoom_ShouldCreateRoomSuccessfully() {
        Rooms room = new Rooms();
        room.setRoomName("Room A");

        Branches branch = new Branches();
        branch.setIdBranch(1L);
        room.setBranch(branch);

        when(branchesService.getAllBranches()).thenReturn(Arrays.asList(branch));
        when(roomsService.getAllRooms()).thenReturn(Arrays.asList());
        when(roomsService.saveRoom(room)).thenReturn(room);

        ResponseEntity<?> response = roomsController.createRoom(room);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(room, response.getBody());
        verify(roomsService, times(1)).saveRoom(room);
    }

    @Test
    void createRoom_ShouldReturnNotFoundIfBranchDoesNotExist() {
        Rooms room = new Rooms();
        room.setRoomName("Room A");

        Branches branch = new Branches();
        branch.setIdBranch(2L);
        room.setBranch(branch);

        when(branchesService.getAllBranches()).thenReturn(Arrays.asList());

        ResponseEntity<?> response = roomsController.createRoom(room);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Branch not found", response.getBody());
        verify(roomsService, never()).saveRoom(any(Rooms.class));
    }

    @Test
    void createRoom_ShouldReturnConflictIfDuplicateRoomNameInBranch() {
        Rooms room = new Rooms();
        room.setRoomName("Room A");

        Branches branch = new Branches();
        branch.setIdBranch(1L);
        room.setBranch(branch);

        Rooms existingRoom = new Rooms();
        existingRoom.setRoomName("Room A");
        existingRoom.setBranch(branch);

        when(branchesService.getAllBranches()).thenReturn(Arrays.asList(branch));
        when(roomsService.getAllRooms()).thenReturn(Arrays.asList(existingRoom));

        ResponseEntity<?> response = roomsController.createRoom(room);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("A room with the same name is already created in the same branch", response.getBody());
        verify(roomsService, never()).saveRoom(any(Rooms.class));
    }

    @Test
    void viewRoom_ShouldReturnRoomIfFound() {
        Rooms room = new Rooms();
        when(roomsService.getRoomById(1L)).thenReturn(Optional.of(room));

        Rooms response = roomsController.viewRoom(1L);

        assertEquals(room, response);
        verify(roomsService, times(1)).getRoomById(1L);
    }

    @Test
    void viewRoom_ShouldReturnNullIfRoomNotFound() {
        when(roomsService.getRoomById(1L)).thenReturn(Optional.empty());

        Rooms response = roomsController.viewRoom(1L);

        assertEquals(null, response);
        verify(roomsService, times(1)).getRoomById(1L);
    }

    @Test
    void updateRoom_ShouldUpdateRoomSuccessfully() {
        Rooms existingRoom = new Rooms();
        existingRoom.setRoomName("Old Room Name");

        Rooms updatedRoom = new Rooms();
        updatedRoom.setRoomName("New Room Name");

        when(roomsService.getRoomById(1L)).thenReturn(Optional.of(existingRoom));
        when(roomsService.saveRoom(existingRoom)).thenReturn(existingRoom);

        Rooms response = roomsController.updateRoom(1L, updatedRoom);

        assertEquals("New Room Name", response.getRoomName());
        verify(roomsService, times(1)).saveRoom(existingRoom);
    }

    @Test
    void updateRoom_ShouldReturnNullIfRoomNotFound() {
        Rooms updatedRoom = new Rooms();
        updatedRoom.setRoomName("New Room Name");

        when(roomsService.getRoomById(1L)).thenReturn(Optional.empty());

        Rooms response = roomsController.updateRoom(1L, updatedRoom);

        assertEquals(null, response);
        verify(roomsService, never()).saveRoom(any(Rooms.class));
    }

    @Test
    void deleteRoom_ShouldDeleteRoomSuccessfully() {
        when(roomsService.deleteRoom(1L)).thenReturn(true);

        boolean response = roomsController.deleteRoom(1L);

        assertEquals(true, response);
        verify(roomsService, times(1)).deleteRoom(1L);
    }

    @Test
    void deleteRoom_ShouldReturnFalseIfRoomNotFound() {
        when(roomsService.deleteRoom(1L)).thenReturn(false);

        boolean response = roomsController.deleteRoom(1L);

        assertEquals(false, response);
        verify(roomsService, times(1)).deleteRoom(1L);
    }
}
