package com.example.demo.controller;
import com.example.demo.model.Branches;
import com.example.demo.service.BranchesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

    public class BranchesControllerTests {

        @Mock
        private BranchesService branchesService;

        @InjectMocks
        private BranchesController branchesController;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        void listBranches_ShouldReturnListOfBranches() {
            Branches branch = new Branches();
            when(branchesService.getAllBranches()).thenReturn(Arrays.asList(branch));

            ResponseEntity<List<Branches>> response = branchesController.listBranches();

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(1, response.getBody().size());
            verify(branchesService, times(1)).getAllBranches();
        }

        @Test
        void viewBranch_ShouldReturnBranchIfFound() {
            Branches branch = new Branches();
            when(branchesService.getBranchById(1L)).thenReturn(branch);

            ResponseEntity<?> response = branchesController.viewBranch(1L);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(branch, response.getBody());
        }

        @Test
        void viewBranch_ShouldReturnNotFoundIfNotFound() {
            when(branchesService.getBranchById(1L)).thenReturn(null);

            ResponseEntity<?> response = branchesController.viewBranch(1L);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertEquals("Branch not found.", response.getBody());
        }

        @Test
        void createBranch_ShouldCreateBranchSuccessfully() throws Exception {
            Branches branch = new Branches();
            branch.setLocation("New Location");

            ResponseEntity<String> response = branchesController.create(branch);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals("Branch created successfully.", response.getBody());
            verify(branchesService, times(1)).createBranch("New Location");
        }

        @Test
        void createBranch_ShouldReturnBadRequestIfLocationIsNullOrEmpty() throws Exception {
            Branches branch = new Branches();

            ResponseEntity<String> response = branchesController.create(branch);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertEquals("Incomplete set of data.", response.getBody());
            verify(branchesService, never()).createBranch(anyString());
        }

        @Test
        void updateBranch_ShouldUpdateBranchSuccessfully() {
            Branches branch = new Branches();
            branch.setIdBranch(1L);
            branch.setLocation("Old Location");

            Branches updatedBranch = new Branches();
            updatedBranch.setLocation("New Location");

            when(branchesService.getBranchById(1L)).thenReturn(branch);

            ResponseEntity<String> response = branchesController.updateBranch(1L, updatedBranch);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals("Branch updated successfully.", response.getBody());
            verify(branchesService, times(1)).saveBranch(branch);
            assertEquals("New Location", branch.getLocation());
        }

        @Test
        void updateBranch_ShouldReturnBadRequestIfBranchNotFound() {
            Branches updatedBranch = new Branches();
            updatedBranch.setLocation("New Location");

            when(branchesService.getBranchById(1L)).thenReturn(null);

            ResponseEntity<String> response = branchesController.updateBranch(1L, updatedBranch);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertEquals("Branch not found.", response.getBody());
            verify(branchesService, never()).saveBranch(any(Branches.class));
        }

        @Test
        void deleteBranch_ShouldDeleteBranchIfExists() {
            when(branchesService.getBranchById(1L)).thenReturn(new Branches());

            ResponseEntity<String> response = branchesController.deleteBranch(1L);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals("Branch deleted successfully.", response.getBody());
            verify(branchesService, times(1)).deleteBranch(1L);
        }

        @Test
        void deleteBranch_ShouldReturnBadRequestIfBranchNotFound() {
            when(branchesService.getBranchById(1L)).thenReturn(null);

            ResponseEntity<String> response = branchesController.deleteBranch(1L);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertEquals("Branch not found.", response.getBody());
            verify(branchesService, never()).deleteBranch(1L);
        }
    }




